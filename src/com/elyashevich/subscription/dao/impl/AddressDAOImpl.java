package com.elyashevich.subscription.dao.impl;

import com.elyashevich.subscription.dao.AbstractDAO;
import com.elyashevich.subscription.dao.AddressDAO;
import com.elyashevich.subscription.entity.Address;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import com.elyashevich.subscription.pool.ConnectionPool;
import com.elyashevich.subscription.pool.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class AddressDAOImpl extends AbstractDAO<Address> implements AddressDAO {
    private static final String INSERT_ADDRESS = "INSERT INTO ADDRESS(country, post_index, city, detail_address) VALUES (?,?,?,?)";
    private static final String SQL_SELECT_ADDRESS_BY_ID = "SELECT country, post_index, city, detail_address, id FROM ADDRESS WHERE id=?";
    private static final String UPDATE_ADDRESS = "UPDATE address SET country=?, post_index=?, city=?, detail_address=?  where id=?";

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean delete(Address entity) {
        return false;
    }

    @Override
    public boolean create(Address entity) {
        return false;
    }

    @Override
    public Address findAddressById(long id) throws DAOTechnicalException {
        Address address = null;
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().defineConnection();
            statement = connection.prepareStatement(SQL_SELECT_ADDRESS_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                address = new Address(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );
                address.setId(resultSet.getLong(5));
            }
        } catch (SQLException e) {
            throw new DAOTechnicalException("exception in AddressDAOImpl",e.getCause());
        } finally {
            close(statement);
            close(connection);
        }
        return address;
    }

    @Override
    public Address createAddress(Address address) throws DAOTechnicalException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionPool.getInstance().defineConnection();
            preparedStatement = connection.prepareStatement(INSERT_ADDRESS, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, address.getCountry());
            preparedStatement.setString(2, address.getPostIndex());
            preparedStatement.setString(3, address.getCity());
            preparedStatement.setString(4, address.getDetailAddress());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                address.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new DAOTechnicalException("exception in AddressDAOImpl", e.getCause());
        } finally {
            close(preparedStatement);
            close(connection);
        }
        return address;
    }

    @Override
    public boolean update(Address address) throws DAOTechnicalException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        boolean result;
        try {
            connection = ConnectionPool.getInstance().defineConnection();
            statement = connection.prepareStatement(UPDATE_ADDRESS);
            statement.setString(1, address.getCountry());
            statement.setString(2, address.getPostIndex());
            statement.setString(3, address.getCity());
            statement.setString(4, address.getDetailAddress());
            statement.setLong(5, address.getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOTechnicalException("exception in AddressDAOImpl", e.getCause());
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }
}
