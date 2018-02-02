package com.elyashevich.subscription.dao;

import com.elyashevich.subscription.entity.Entity;
import com.elyashevich.subscription.exception.DAOTechnicalException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class AbstractDAO<T extends Entity> {
    private static final Logger LOGGER = LogManager.getLogger();

    public abstract List<T> findAll() throws DAOTechnicalException;

    public abstract boolean delete(int id);

    public abstract boolean delete(T entity);

    public abstract boolean create(T entity) throws DAOTechnicalException;

    public abstract boolean update(T entity) throws DAOTechnicalException;

    void close(Statement statement){
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
        }
    }
    void close(Connection connection) throws DAOTechnicalException {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new DAOTechnicalException(e.getMessage(), e.getCause());
        }
    }
}

