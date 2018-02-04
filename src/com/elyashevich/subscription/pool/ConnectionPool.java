package com.elyashevich.subscription.pool;

import com.elyashevich.subscription.exception.ConnectionTechnicalException;
import com.elyashevich.subscription.proxy.ProxyConnection;
import com.elyashevich.subscription.util.ConnectionDB;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger();
    private static AtomicBoolean flag = new AtomicBoolean();
    private static ConnectionPool instance;
    private static Lock lock = new ReentrantLock();
    private BlockingQueue<ProxyConnection> connectionQueue;
    private final int POOL_SIZE = 45;

    private ConnectionPool() throws ConnectionTechnicalException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.FATAL, e.getMessage());
            throw new RuntimeException(e.getCause());
        }
        connectionQueue = new ArrayBlockingQueue<>(POOL_SIZE);
        for (int i=0; i<POOL_SIZE; i++){
            ProxyConnection connection = null;
            try {
                connection = new ProxyConnection(ConnectionDB.createConnection());
            } catch (SQLException e) {
                throw new ConnectionTechnicalException(e.getCause());
            }
            connectionQueue.offer(connection);
        }

    }

    public static ConnectionPool getInstance() {
        if (!flag.get()) {
            try {
                lock.lock();
                if (!flag.get()) {
                    instance = new ConnectionPool();
                    flag.set(true);
                }
            } catch (ConnectionTechnicalException e) {
                LOGGER.log(Level.ERROR, e.getMessage());
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public ProxyConnection getConnection(){
        ProxyConnection connection = null;
        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
             //connection->daoexception->receiver-service->command_exception->error_page
        }
        return connection;
    }

    public void releaseConnection(ProxyConnection connection) throws ConnectionTechnicalException {
        try {
            if (connection.getAutoCommit()){
                try {
                    connectionQueue.put(connection);
                } catch (InterruptedException e) {
                    LOGGER.log(Level.ERROR, e.getMessage());
                }
            }
        } catch (SQLException e) {
            throw new ConnectionTechnicalException(e.getCause());
        }
    }

    public void destroyConnection() {
        for (int i=0; i<POOL_SIZE; i++){
            ProxyConnection connection = null;
            try {
                connection = connectionQueue.take();
                connection.closeConnection();
            } catch (InterruptedException | SQLException e) {
                LOGGER.log(Level.ERROR, e.getMessage());
            }
        }
    }
}
