package com.elyashevich.subscription.dao;

import com.elyashevich.subscription.proxy.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionHelper {
    private static final Logger LOGGER = LogManager.getLogger();
    private Connection connection = ConnectionPool.getInstance().getConnection();
    public void beginTransaction(AbstractDAO dao, AbstractDAO...daos){
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
        }
        for (AbstractDAO abstractDAO: daos){
            abstractDAO.setConnection(connection);
        }
    }
    public void endTransaction(){
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
        }
    }
    public void commit(){
        try {
            connection.commit();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
        }
    }
    public void rollback(){
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
        }
    }
}
