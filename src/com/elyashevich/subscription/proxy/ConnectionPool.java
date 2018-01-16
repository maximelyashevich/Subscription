package com.elyashevich.subscription.proxy;

import com.elyashevich.subscription.util.ConnectionDB;

import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {

    private static AtomicBoolean flag = new AtomicBoolean();
    private static ConnectionPool instance;
    private static Lock lock = new ReentrantLock();
    private BlockingQueue<ProxyConnection> connectionQueue;
    private final int POOL_SIZE = 45;

    private ConnectionPool() throws SQLException{
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connectionQueue = new ArrayBlockingQueue<>(POOL_SIZE);
        for (int i=0; i<POOL_SIZE; i++){
            ProxyConnection connection = new ProxyConnection(ConnectionDB.createConnection());
            connectionQueue.offer(connection);
        }
    }

    public static ConnectionPool getInstance(){
        if (!flag.get()) {
            try {
                lock.lock();
                if (!flag.get()) {
                    instance = new ConnectionPool();
                    flag.set(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
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
            e.printStackTrace();
        }
        return connection;
    }

    public void releaseConnection(ProxyConnection connection) throws SQLException{
        if (connection.getAutoCommit()){
            try {
                connectionQueue.put(connection);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void destroyConnection() throws InterruptedException, SQLException {
        for (int i=0; i<POOL_SIZE; i++){
            ProxyConnection connection = connectionQueue.take();
            connection.closeConnection();
        }
    }
}
