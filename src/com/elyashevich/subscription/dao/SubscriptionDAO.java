package com.elyashevich.subscription.dao;

import com.elyashevich.subscription.entity.PaperEdition;
import com.elyashevich.subscription.entity.Subscription;
import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import com.elyashevich.subscription.proxy.ConnectionPool;
import com.elyashevich.subscription.proxy.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionDAO extends AbstractDAO<Subscription> {
    private static final String SQL_SELECT_ALL_SUBSCRIPTIONS = "SELECT id, user_id, registration_date, finish_date, price FROM subscription_db.subscription\n" +
            "JOIN subscription_db.subscription_papers ON subscription_papers.subscription_id = id;";
    private static final String SQL_SELECT_ALL_SUBSCRIPTIONS_BY_USER_ID = "SELECT subscription_id, papers.id, registration_date, finish_date, subscription.price FROM subscription_db.subscription_papers " +
            "JOIN subscription_db.papers ON subscription_papers.papers_id = papers.id " +
            "JOIN subscription_db.subscription ON subscription_papers.subscription_id = subscription.id " +
            "JOIN subscription_db.user ON user.id = subscription.user_id WHERE user.id=?;";
    @Override
    public List<Subscription> findAll() throws DAOTechnicalException {
        List<Subscription> subscriptions = new ArrayList<>();
        ProxyConnection cn = null;
        Statement st = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            st = cn.createStatement();

            ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_SUBSCRIPTIONS);
            Subscription subscription;
            User user;
            while (resultSet.next()) {
                user = new UserDAOImpl().findUserById(resultSet.getLong(2));
                subscription = new Subscription(resultSet.getDate(3).toLocalDate(),
                        resultSet.getDate(4).toLocalDate(), resultSet.getBigDecimal(5));
                subscription.setUser(user);
                subscriptions.add(subscription);
            }
        } catch (SQLException e) {
            throw new DAOTechnicalException(e.getCause());
        } finally {
            close(st);
            if (cn!=null) {
                close(cn);
            }
        }
        return subscriptions;
    }

    public List<Subscription> findAllByUserID(long id) throws DAOTechnicalException {
        List<Subscription> subscriptions= new ArrayList<>();
        ProxyConnection cn = null;
        PreparedStatement st = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            st = cn.prepareStatement(SQL_SELECT_ALL_SUBSCRIPTIONS_BY_USER_ID);
            st.setLong(1, id);
            ResultSet resultSet = st.executeQuery();
            Subscription subscription;
            PaperEdition paperEdition;
            while (resultSet.next()) {
                    subscription = new Subscription(resultSet.getDate(3).toLocalDate(),
                            resultSet.getDate(4).toLocalDate(), resultSet.getBigDecimal(5));
                    paperEdition = new PaperDAOImpl().findPaperById(resultSet.getLong(2));
                    subscription.setPaperEdition(paperEdition);
                    subscription.setId(resultSet.getLong(1));
                    subscriptions.add(subscription);
            }
        } catch (SQLException e) {
            throw new DAOTechnicalException(e.getCause());
        } finally {
            close(st);
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

    @Override
    public boolean delete(Subscription entity) {
        return false;
    }

    @Override
    public boolean create(Subscription entity) throws DAOTechnicalException {
        return false;
    }

    @Override
    public boolean update(Subscription entity) {
        return false;
    }

}
