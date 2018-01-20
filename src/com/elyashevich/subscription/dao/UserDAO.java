package com.elyashevich.subscription.dao;

import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import com.elyashevich.subscription.proxy.ConnectionPool;
import com.elyashevich.subscription.proxy.ProxyConnection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAO extends AbstractDAO<User>{
    private static final String INSERT_USER = "INSERT INTO USER(user_name, password,email,first_name, last_name, dob) VALUES (?,?,?,?,?,?)";
    private static final String SQL_SELECT_USER_BY_PASSWORD = "SELECT user_name, password, first_name, last_name FROM USER WHERE user_name=? and password=?";
    @Override

    public List<User> findAll() {
        return null;
    }
    public String findUser(String login, String password) throws DAOTechnicalException {
        String name = null;
        ProxyConnection cn = null;
        PreparedStatement st = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            st = cn.prepareStatement(SQL_SELECT_USER_BY_PASSWORD);
            st.setString(1, login);
            st.setString(2, password);
            System.out.println(login+" "+password);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                name = resultSet.getString(3)+" "+resultSet.getString(4);
            }
        } catch (SQLException e) {
           throw new DAOTechnicalException(e.getCause());
        } finally {
            close(st);
            if (cn!=null) {
                close(cn);
            }
        }
        return name;
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

