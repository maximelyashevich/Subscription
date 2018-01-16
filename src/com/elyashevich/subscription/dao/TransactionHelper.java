package com.elyashevich.subscription.dao;

import com.elyashevich.subscription.proxy.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionHelper {
    private Connection connection = ConnectionPool.getInstance().getConnection();
    public void beginTransaction(AbstractDAO dao, AbstractDAO...daos){
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (AbstractDAO abstractDAO: daos){
            abstractDAO.setConnection(connection);
        }
    }
    public void endTransaction(){
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void commit(){
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void rollback(){
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
