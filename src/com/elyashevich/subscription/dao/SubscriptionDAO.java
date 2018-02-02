package com.elyashevich.subscription.dao;

import com.elyashevich.subscription.entity.PaperEdition;
import com.elyashevich.subscription.entity.Subscription;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import com.elyashevich.subscription.proxy.ConnectionPool;
import com.elyashevich.subscription.proxy.ProxyConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionDAO extends AbstractDAO<Subscription> {
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
        ProxyConnection cn = null;
        Statement st = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            st = cn.createStatement();
            ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_SUBSCRIPTIONS);

            while (resultSet.next()) {
                subscriptions.addAll(findAllByID(resultSet.getLong(1)));
            }
        } catch (SQLException e) {
            throw new DAOTechnicalException(e.getCause());
        } finally {
            if (st!=null) {
                close(st);
            }
            if (cn!=null) {
                close(cn);
            }
        }
        return subscriptions;
    }

    public List<Subscription> findAllWithFinishedAction() throws DAOTechnicalException {
        List<Subscription> subscriptions = new ArrayList<>();
        ProxyConnection cn = null;
        Statement st = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            st = cn.createStatement();

            ResultSet resultSet = st.executeQuery(SQL_SELECT_SUBSCRIPTIONS_WITH_FINISHED_ACTION);
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
            throw new DAOTechnicalException(e.getCause());
        } finally {
            if (st!=null) {
                close(st);
            }
            if (cn!=null) {
                close(cn);
            }
        }
        return subscriptions;
    }

    public static void main(String[] args) {
        SubscriptionDAO subscriptionDAO = new SubscriptionDAO();
        try {
            System.out.println(subscriptionDAO.findAllByUserID(49));
        } catch (DAOTechnicalException e) {
            e.printStackTrace();
        }
    }

    private List<Subscription> findAllByID(long id) throws DAOTechnicalException {
        List<Subscription> subscriptions= new ArrayList<>();
        ProxyConnection cn = null;
        PreparedStatement st = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            st = cn.prepareStatement(SQL_SELECT_ALL_SUBSCRIPTIONS_BY_ID);
            st.setLong(1, id);
            ResultSet resultSet = st.executeQuery();
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
            throw new DAOTechnicalException(e.getCause());
        } finally {
            if (st!=null) {
                close(st);
            }
            if (cn!=null) {
                close(cn);
            }
        }
        return subscriptions;
    }

    public List<Subscription> findAllByUserID(long id) throws DAOTechnicalException {
        List<Subscription> subscriptions = new ArrayList<>();
        ProxyConnection cn = null;
        PreparedStatement st = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            st = cn.prepareStatement(SQL_SELECT_ALL_SUBSCRIPTIONS_BY_USER_ID);
            st.setLong(1, id);
            ResultSet resultSet = st.executeQuery();

            while (resultSet.next()) {
                    subscriptions.addAll(findAllByID(resultSet.getLong(1)));
            }
        } catch (SQLException e) {
            throw new DAOTechnicalException(e.getCause());
        } finally {
            if (st!=null) {
                close(st);
            }
            if (cn!=null) {
                close(cn);
            }
        }
        return subscriptions;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }


    public boolean deleteBySubscriptionAndPaperId(long subscriptionId, long paperId) throws DAOTechnicalException {
        ProxyConnection cn = null;
        PreparedStatement st = null;
        boolean result = false;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            st = cn.prepareStatement(SQL_DELETE_SUBSCRIPTION_BY_ID_AND_PAPERS_ID);
            st.setLong(1, subscriptionId);
            st.setLong(2, paperId);
            //result = st.executeUpdate()>0;
            System.out.println("->Deleted!");
        } catch (SQLException e) {
            throw new DAOTechnicalException(e.getCause());
        } finally {
                close(st);
                close(cn);
        }
        return result;
    }

    @Override
    public boolean delete(Subscription entity) {
        return false;
    }

    @Override
    public boolean create(Subscription subscription) throws DAOTechnicalException {
        boolean flag = false;

        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_CREATE_SUBSCRIPTION, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, subscription.getUser().getId());
            preparedStatement.setDate(2, Date.valueOf(subscription.getSubscriptionRegistration()));
            preparedStatement.setBigDecimal(3, subscription.getPrice());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next())
            {
                int id = rs.getInt(1);
                subscription.setId(id);
            }
            flag = true;
        }
        catch(SQLException e) {
            throw new DAOTechnicalException(e.getCause());
        }
        finally {
            if (preparedStatement!=null) {
                close(preparedStatement);
            }
            if (connection!=null) {
                close(connection);
            }
        }
        return flag;
    }

    public boolean createWithPaper(Subscription subscription, long paperId, LocalDate finishDate) throws DAOTechnicalException {
        boolean flag = false;

        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_CREATE_SUBSCRIPTION_PAPER);
            preparedStatement.setLong(1, subscription.getId());
            preparedStatement.setLong(2, paperId);
            preparedStatement.setDate(3, Date.valueOf(subscription.getSubscriptionRegistration()));
            preparedStatement.executeUpdate();
            flag = true;
        }
        catch(SQLException e) {
            throw new DAOTechnicalException(e.getCause());
        }
        finally {
            if (preparedStatement!=null) {
                close(preparedStatement);
            }
            if (connection!=null) {
                close(connection);
            }
        }
        return flag;
    }

    @Override
    public boolean update(Subscription entity) {
        return false;
    }

}
