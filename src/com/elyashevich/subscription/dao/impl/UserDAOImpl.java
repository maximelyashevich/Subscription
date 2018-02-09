package com.elyashevich.subscription.dao.impl;

import com.elyashevich.subscription.command.ClientType;
import com.elyashevich.subscription.dao.AbstractDAO;
import com.elyashevich.subscription.dao.AddressDAO;
import com.elyashevich.subscription.dao.UserDAO;
import com.elyashevich.subscription.entity.Address;
import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import com.elyashevich.subscription.pool.ConnectionPool;
import com.elyashevich.subscription.pool.ProxyConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl extends AbstractDAO<User> implements UserDAO {
    private static final String INSERT_USER = "INSERT INTO USER(user_name, password,email,first_name, last_name, dob, address_id) VALUES (?,?,?,?,?,?,?)";
    private static final String SQL_SELECT_USER_BY_PASSWORD = "SELECT id FROM USER WHERE user_name=? AND password=?";
    private static final String SQL_SELECT_USER_BY_LOGIN = "SELECT id FROM USER WHERE user_name=?";
    private static final String SQL_SELECT_USER_BY_EMAIL = "SELECT id FROM USER WHERE email=?";
    private static final String SQL_SELECT_ALL_USERS = "SELECT id FROM USER";
    private static final String SQL_SELECT_USER_BY_ID = "SELECT user_name, password, role, email, is_active, first_name, last_name, address_id, dob, amount, image_path, id FROM USER WHERE id=?";
    private static final String UPDATE_USER = "UPDATE user SET first_name=?, last_name=?, image_path=?, address_id=?, dob=?, is_active=?  WHERE id=?";
    private static final String UPDATE_USER_AMOUNT_BY_ID = "UPDATE user SET amount=? WHERE user.id=?";
    private static final String TINY_INT_YES = "1";

    @Override
    public List<User> findAll() throws DAOTechnicalException {
        List<User> users = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().defineConnection();
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS);
            User user;
            while (resultSet.next()) {
                user = findUserById(resultSet.getLong(1));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DAOTechnicalException("exception in UserDAOImpl", e.getCause());
        } finally {
            close(statement);
            close(connection);
        }
        return users;
    }

    @Override
    public User findUser(String login, String password) throws DAOTechnicalException {
        User user = null;
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().defineConnection();
            statement = connection.prepareStatement(SQL_SELECT_USER_BY_PASSWORD);
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = findUserById(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DAOTechnicalException("exception in UserDAOImpl", e.getCause());
        } finally {
            close(statement);
            close(connection);
        }
        return user;
    }


    @Override
    public User findUserByLogin(String login) throws DAOTechnicalException {
        return defineUserByParameter(login, SQL_SELECT_USER_BY_LOGIN);
    }

    private User defineUserByParameter(String login, String sqlSelectUserByLogin) throws DAOTechnicalException {
        User user = null;
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().defineConnection();
            statement = connection.prepareStatement(sqlSelectUserByLogin);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = findUserById(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DAOTechnicalException("exception in UserDAOImpl", e.getCause());
        } finally {
            close(statement);
            close(connection);
        }
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws DAOTechnicalException {
        return defineUserByParameter(email, SQL_SELECT_USER_BY_EMAIL);
    }

    @Override
    public User findUserById(long id) throws DAOTechnicalException {
        User user = null;
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        AddressDAO addressDAO = new AddressDAOImpl();
        try {
            connection = ConnectionPool.getInstance().defineConnection();
            statement = connection.prepareStatement(SQL_SELECT_USER_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        ClientType.valueOf(resultSet.getString(3).toUpperCase()),
                        resultSet.getString(4),
                        TINY_INT_YES.equals(resultSet.getString(5)),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getDate(9).toLocalDate(),
                        resultSet.getBigDecimal(10)
                );
                user.setAddress(addressDAO.findAddressById(resultSet.getLong(8)));
                user.setImagePath(resultSet.getString(11));
                user.setId(resultSet.getLong(12));
            }
        } catch (SQLException e) {
            throw new DAOTechnicalException("exception in UserDAOImpl", e.getCause());
        } finally {
            close(statement);
            close(connection);
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
    public boolean create(User user) {
        return false;
    }

    @Override
    public boolean create(User user, Address address) throws DAOTechnicalException {
        boolean flag;

        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        AddressDAO addressDAO = new AddressDAOImpl();
        Address address1 = addressDAO.createAddress(address);
        try {
            connection = ConnectionPool.getInstance().defineConnection();
            preparedStatement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getFirstName());
            preparedStatement.setString(5, user.getLastName());
            preparedStatement.setDate(6, Date.valueOf(user.getBirthday()));
            preparedStatement.setLong(7, address1.getId());
            preparedStatement.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            throw new DAOTechnicalException("exception in UserDAOImpl", e.getCause());
        } finally {
            close(preparedStatement);
            close(connection);
        }
        return flag;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean update(User user, Address address) throws DAOTechnicalException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        AddressDAO addressDAO = new AddressDAOImpl();
        Address address1 = addressDAO.createAddress(address);
        boolean result;
        try {
            connection = ConnectionPool.getInstance().defineConnection();
            statement = connection.prepareStatement(UPDATE_USER);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getImagePath());
            statement.setLong(4, address1.getId());
            statement.setDate(5, Date.valueOf(user.getBirthday()));
            statement.setInt(6, user.isAvailability() ? 1 : 0);
            statement.setLong(7, user.getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOTechnicalException("exception in UserDAOImpl", e.getCause());
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public User updateAmount(long id, BigDecimal price) throws DAOTechnicalException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().defineConnection();
            statement = connection.prepareStatement(UPDATE_USER_AMOUNT_BY_ID);
            statement.setBigDecimal(1, price);
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOTechnicalException("exception in UserDAOImpl", e.getCause());
        } finally {
            close(statement);
            close(connection);
        }
        return findUserById(id);
    }
}

