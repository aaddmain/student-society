package com.gzy.utils;
// 导入模块
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class Database {

    private static Connection connection = null;

     // 使用ThreadLocal来存储当前线程的连接
    private static final ThreadLocal<Connection> threadLocalConnection = new ThreadLocal<>();


     // 事务状态标记
    private static final ThreadLocal<Boolean> transactionActive = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };


    public static Connection conn(){

         // 如果当前线程已经有连接且事务正在进行中，则返回该连接
        Connection conn = threadLocalConnection.get();
        if (conn != null && transactionActive.get()) {
            return conn;
        }

        String url = "jdbc:mysql://localhost:3306/banksystem?" +
                "verifyServerCertificate=false&useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC";
        try {
            //加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //建立连接，用户名：root，密码：352987
            conn = DriverManager.getConnection(url, "root", "352987");
            // 如果是事务中，保存连接到ThreadLocal
            if (transactionActive.get()) {
                threadLocalConnection.set(conn);
            }
        }catch (SQLException e){
            System.err.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            System.err.println("MySQL driver not found: " + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }


     /**
     * 开始事务
     */
    public static void beginTransaction() {
        try {
            Connection conn = conn();
            if (conn == null) {
                throw new RuntimeException("Failed to establish database connection");
            }
            conn.setAutoCommit(false);
            threadLocalConnection.set(conn);
            transactionActive.set(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 提交事务
     */
    public static void commitTransaction() {
        try {
            Connection conn = threadLocalConnection.get();
            if (conn != null) {
                conn.commit();
                conn.setAutoCommit(true);
                threadLocalConnection.remove();
                transactionActive.set(false);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 回滚事务
     */
    public static void rollbackTransaction() {
        try {
            Connection conn = threadLocalConnection.get();
            if (conn != null) {
                conn.rollback();
                conn.setAutoCommit(true);
                threadLocalConnection.remove();
                transactionActive.set(false);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}