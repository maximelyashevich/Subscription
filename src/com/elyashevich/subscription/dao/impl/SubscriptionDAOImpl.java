package com.elyashevich.subscription.dao.impl;

import com.elyashevich.subscription.dao.AbstractDAO;
import com.elyashevich.subscription.dao.SubscriptionDAO;
import com.elyashevich.subscription.entity.PaperEdition;
import com.elyashevich.subscription.entity.Subscription;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import com.elyashevich.subscription.pool.ConnectionPool;
import com.elyashevich.subscription.pool.ProxyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionDAOImpl extends AbstractDAO<Subscription> implements SubscriptionDAO {
    private static final String SQL_SELECT_ALL_SUBSCRIPTIONS = "SELECT id FROM subscription_db.subscription";
    private static final String SQL_SELECT_ALL_SUBSCRIPTIONS_BY_ID = "SELECT subscription.id, user_id, papers.id, registration_date, finish_date, subscription.price " +
            "FROM subscription_db.subscription " +
            "JOIN subscription_db.subscription_papers ON subscription_papers.subscription_id = subscription.id " +
            "JOIN subscription_db.papers ON subscription_papers.papers_id = papers.id " +
            "where subscription.id=?";
    private static final String SQL_SELECT_ALL_SUBSCRIPTIONS_BY_USER_ID = "SELECT subscription.id FROM subscription " +
            "JOIN subscription_db.user ON user.id = subscription.user_id WHERE user.id=?";
    private static final String SQL_SELECT_SUBSCRIPTIONS_WITH_FINISHED_ACTION = "SELECT subscription_id, paper_id, start_sub, finish_sub, price " +
            "            FROM (SELECT subscription_id, papers.id as 'paper_id', registration_date as 'start_sub', " +
            "            finish_date as 'finish_sub', subscription.price AS 'price', CURDATE(), DATEDIFF(finish_date, CURDATE()) AS 'q1' FROM subscription_db.subscription_papers " +
            "            JOIN subscription_db.papers ON subscription_papers.papers_id = papers.id " +
            "            JOIN subscription_db.subscription ON subscription_papers.subscription_id = subscription.id " +
            "            JOIN subscription_db.user ON user.id = subscription.user_id) AS w1 WHERE q1<0";
    private static final String SQL_DELETE_SUBSCRIPTION_BY_ID_AND_PAPERS_ID = "DELETE FROM subscription_papers " +
            "WHERE subscription_id = ? and papers_id=?";
    private static final String SQL_CREATE_SUBSCRIPTION = "INSERT INTO subscription(user_id, registration_date, price) VALUES(?, ?, ?);";
    private static final String SQL_CREATE_SUBSCRIPTION_PAPER = "INSERT INTO subscription_papers(subscription_id, papers_id, finish_date) VALUES(?, ?, ?);";

    @Override
    public List<Subscription> findAll() throws DAOTechnicalException {
        List<Subscription> subscriptions = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().defineConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_SUBSCRIPTIONS);

            while (resultSet.next()) {
                subscriptions.addAll(findAllByID(resultSet.getLong(1)));
            }
        } catch (SQLException e) {
            throw new DAOTechnicalException("exception in SubscriptionDAOImpl", e.getCause());
        } finally {
            close(statement);
            close(connection);
        }
        return subscriptions;
    }

    @Override
    public List<Subscription> findAllWithFinishedAction() throws DAOTechnicalException {
        List<Subscription> subscriptions = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().defineConnection();
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(SQL_SELECT_SUBSCRIPTIONS_WITH_FINISHED_ACTION);
            PaperEdition paperEdition;
            Subscription subscription;
            while (resultSet.next()) {
                paperEdition = new PaperDAOImpl().findPaperById(resultSet.getLong(2));
                subscription = new Subscription(resultSet.getDate(3).toLocalDate(),
                        resultSet.getDate(4).toLocalDate(), resultSet.getBigDecimal(5));
                subscription.setId(resultSet.getLong(1));
                subscription.setPaperEdition(paperEdition);
                subscriptions.add(subscription);
            }
        } catch (SQLException e) {
            throw new DAOTechnicalException("exception in SubscriptionDAOImpl", e.getCause());
        } finally {
            close(statement);
            close(connection);
        }
        return subscriptions;
    }

    @Override
    public List<Subscription> findAllByID(long id) throws DAOTechnicalException {
        List<Subscription> subscriptions = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().defineConnection();
            statement = connection.prepareStatement(SQL_SELECT_ALL_SUBSCRIPTIONS_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Subscription subscription;
            while (resultSet.next()) {
                subscription = new Subscription(resultSet.getDate(4).toLocalDate(),
                        resultSet.getDate(5).toLocalDate(), resultSet.getBigDecimal(6));
                subscription.setId(resultSet.getLong(1));
                subscription.setPaperEdition(new PaperDAOImpl().findPaperById(resultSet.getLong(3)));
                subscription.setUser(new UserDAOImpl().findUserById(resultSet.getLong(2)));
                subscriptions.add(subscription);
            }
        } catch (SQLException e) {
            throw new DAOTechnicalException("exception in SubscriptionDAOImpl", e.getCause());
        } finally {
            close(statement);
            close(connection);
        }
        return subscriptions;
    }

    @Override
    public List<Subscription> findAllByUserID(long id) throws DAOTechnicalException {
        List<Subscription> subscriptions = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().defineConnection();
            statement = connection.prepareStatement(SQL_SELECT_ALL_SUBSCRIPTIONS_BY_USER_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                subscriptions.addAll(findAllByID(resultSet.getLong(1)));
            }
        } catch (SQLException e) {
            throw new DAOTechnicalException("exception in SubscriptionDAOImpl", e.getCause());
        } finally {
            close(statement);
            close(connection);
        }
        return subscriptions;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean deleteBySubscriptionAndPaperId(long subscriptionId, long paperId) throws DAOTechnicalException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        boolean result;
        try {
            connection = ConnectionPool.getInstance().defineConnection();
            statement = connection.prepareStatement(SQL_DELETE_SUBSCRIPTION_BY_ID_AND_PAPERS_ID);
            statement.setLong(1, subscriptionId);
            statement.setLong(2, paperId);
            result = statement.executeUpdate()>0;
        } catch (SQLException e) {
            throw new DAOTechnicalException("exception in SubscriptionDAOImpl", e.getCause());
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public boolean delete(Subscription entity) {
        return false;
    }

    @Override
    public boolean create(Subscription subscription) throws DAOTechnicalException {
        boolean flag;

        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionPool.getInstance().defineConnection();
            preparedStatement = connection.prepareStatement(SQL_CREATE_SUBSCRIPTION, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, subscription.getUser().getId());
            preparedStatement.setDate(2, Date.valueOf(subscription.getSubscriptionRegistration()));
            preparedStatement.setBigDecimal(3, subscription.getPrice());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                subscription.setId(rs.getInt(1));
            }
            flag = true;
        } catch (SQLException e) {
            throw new DAOTechnicalException("exception in SubscriptionDAOImpl", e.getCause());
        } finally {
            close(preparedStatement);
            close(connection);
        }
        return flag;
    }

    @Override
    public void createWithPaper(Subscription subscription, long paperId) throws DAOTechnicalException {

        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionPool.getInstance().defineConnection();
            preparedStatement = connection.prepareStatement(SQL_CREATE_SUBSCRIPTION_PAPER);
            preparedStatement.setLong(1, subscription.getId());
            preparedStatement.setLong(2, paperId);
            preparedStatement.setDate(3, Date.valueOf(subscription.getSubscriptionFinish()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOTechnicalException("exception in SubscriptionDAOImpl", e.getCause());
        } finally {
            close(preparedStatement);
            close(connection);
        }
    }

    @Override
    public boolean update(Subscription entity) {
        return false;
    }

}
