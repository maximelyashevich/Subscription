package com.elyashevich.subscription.dao;

import com.elyashevich.subscription.command.client.ClientType;
import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import com.elyashevich.subscription.proxy.ConnectionPool;
import com.elyashevich.subscription.proxy.ProxyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends AbstractDAO<User>{
    private static final String INSERT_USER = "INSERT INTO USER(user_name, password,email,first_name, last_name, dob) VALUES (?,?,?,?,?,?)";
    private static final String SQL_SELECT_USER_BY_PASSWORD = "SELECT user_name, password, role, email, is_active, first_name, last_name, address_id, dob, amount FROM USER WHERE user_name=? and password=?";
    private static final String SQL_SELECT_ALL_USERS = "SELECT user_name, password, role, email, is_active, first_name, last_name, address_id, dob, amount FROM USER";

    @Override
    public List<User> findAll() throws DAOTechnicalException {
        List<User> paperEditions = new ArrayList<>();
        ProxyConnection cn = null;
        Statement st = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            st = cn.createStatement();

            ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_USERS);
            while (resultSet.next()) {
                paperEditions.add(new User(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        ClientType.valueOf(resultSet.getString(3).toUpperCase()),
                        resultSet.getString(4),
                        "1".equals(resultSet.getString(5)),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getLong(8),
                        resultSet.getDate(9).toLocalDate(),
                        resultSet.getBigDecimal(10)
                        ));
            }
        } catch (SQLException e) {
            throw new DAOTechnicalException(e.getCause());
        } finally {
            close(st);
            if (cn!=null) {
                close(cn);
            }
        }
        return paperEditions;
    }
    public User findUser(String login, String password) throws DAOTechnicalException {
        User user = null;
        ProxyConnection cn = null;
        PreparedStatement st = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            st = cn.prepareStatement(SQL_SELECT_USER_BY_PASSWORD);
            st.setString(1, login);
            st.setString(2, password);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                 user = new User(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        ClientType.valueOf(resultSet.getString(3).toUpperCase()),
                        resultSet.getString(4),
                        "1".equals(resultSet.getString(5)),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getLong(8),
                        resultSet.getDate(9).toLocalDate(),
                        resultSet.getBigDecimal(10)
                );
            }
        } catch (SQLException e) {
           throw new DAOTechnicalException(e.getCause());
        } finally {
            close(st);
            if (cn!=null) {
                close(cn);
            }
        }
        return user;
    }
    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean delete(User entity) {
        return false;
    }

    @Override
    public boolean create(User user) throws DAOTechnicalException {
        boolean flag = false;

        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(INSERT_USER);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getFirstName());
            preparedStatement.setString(5, user.getLastName());
            preparedStatement.setDate(6, Date.valueOf(user.getBirthday()));

            if (findUser(user.getUserName(), user.getPassword())==null){
                /////////////
                //CHECK EMAIL
                /////////////
                preparedStatement.executeUpdate();
                flag = true;
            }
        }
        catch(SQLException e) {
            throw new DAOTechnicalException(e.getCause());
        }
        finally {
            close(preparedStatement);
            if (connection!=null) {
                close(connection);
            }
        }
        return flag;
    }

    @Override
    public User update(User entity) {
        return null;
    }
}

