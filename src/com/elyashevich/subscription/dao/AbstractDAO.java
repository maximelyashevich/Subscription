package com.elyashevich.subscription.dao;

import com.elyashevich.subscription.entity.Entity;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class AbstractDAO<T extends Entity> {
    protected Connection connection ;

    public abstract List<T> findAll();

    public abstract boolean delete(int id);

    public abstract boolean delete(T entity);

    public abstract boolean create(T entity);

    public abstract T update(T entity);

    public void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void setConnection(Connection connection) {
        this.connection = connection;
    }

}

