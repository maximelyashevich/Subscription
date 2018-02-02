package com.elyashevich.subscription.dao;

import com.elyashevich.subscription.entity.Credit;
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

public class CreditDAO extends AbstractDAO<Credit> {
    private static final String SQL_SELECT_ALL_CREDITS = "SELECT id, user_id, debt, interest_rate, payoff, is_available FROM CREDIT";
    private static final String SQL_SELECT_ALL_CREDITS_BY_USER_ID = "SELECT id, user_id, debt, interest_rate, payoff, is_available FROM CREDIT where user_id=?";

    @Override
    public List<Credit> findAll() throws DAOTechnicalException {
        List<Credit> credits = new ArrayList<>();
        ProxyConnection cn = null;
        Statement st = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            st = cn.createStatement();

            ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_CREDITS);
            defineCreditListStructure(credits, resultSet);
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
        return credits;
    }

    public List<Credit> findCreditByUserId(long id) throws DAOTechnicalException {
        List<Credit> credits = new ArrayList<>();
        ProxyConnection cn = null;
        PreparedStatement st = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            st = cn.prepareStatement(SQL_SELECT_ALL_CREDITS_BY_USER_ID);
            st.setLong(1, id);
            ResultSet resultSet = st.executeQuery();
            defineCreditListStructure(credits, resultSet);
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
        return credits;
    }

    public static void main(String[] args) throws DAOTechnicalException {
        CreditDAO creditDAO = new CreditDAO();
        System.out.println(creditDAO.findCreditByUserId(2));
    }

    private void defineCreditListStructure(List<Credit> credits, ResultSet resultSet) throws SQLException, DAOTechnicalException {
        User user;
        Credit credit;
        while (resultSet.next()) {
            user = new UserDAOImpl().findUserById(resultSet.getLong(2));
            credit = new Credit(
                    resultSet.getBigDecimal(3),
                    resultSet.getBigDecimal(4),
                    resultSet.getInt(5),
                    resultSet.getBoolean(6));
            credit.setId(resultSet.getLong(1));
            credit.addUser(user);
            credits.add(credit);
        }
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean delete(Credit entity) {
        return false;
    }

    @Override
    public boolean create(Credit entity) throws DAOTechnicalException {
        return false;
    }

    @Override
    public boolean update(Credit entity) throws DAOTechnicalException {
        return false;
    }
}
