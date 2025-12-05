package com.gzy.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseFixer {
    
    /**
     * 检查并修复user_amount表结构
     * @return true表示修复成功，false表示修复失败
     */
    public static boolean fixUserAmountTable() {
        Connection conn = null;
        try {
            conn = Database.conn();
            if (conn == null) {
                System.err.println("无法获取数据库连接");
                return false;
            }
            
            // 检查表是否存在
            if (!tableExists(conn, "user_amount")) {
                System.out.println("user_amount表不存在，创建新表");
                return createUserAmountTable(conn);
            }
            
            // 检查表结构
            List<String> existingColumns = getTableColumns(conn, "user_amount");
            
            // 检查必需的列是否存在
            if (!existingColumns.contains("amount")) {
                System.out.println("添加缺失的amount列");
                try {
                    Statement stmt = conn.createStatement();
                    stmt.execute("ALTER TABLE user_amount ADD COLUMN amount BIGINT DEFAULT 0");
                    stmt.close();
                    System.out.println("amount列添加成功");
                } catch (SQLException e) {
                    System.err.println("添加amount列失败: " + e.getMessage());
                    return false;
                }
            }
            
            // 检查其他必需的列
            String[] requiredColumns = {"id", "user_id", "balance", "state", "is_deleted", 
                                       "create_by", "update_by", "create_time", "update_time"};
            
            for (String column : requiredColumns) {
                if (!existingColumns.contains(column)) {
                    System.err.println("缺少必需的列: " + column);
                    return false;
                }
            }
            
            System.out.println("user_amount表结构检查完成");
            return true;
        } catch (Exception e) {
            System.err.println("修复user_amount表时发生错误: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * 检查表是否存在
     */
    private static boolean tableExists(Connection conn, String tableName) throws SQLException {
        String sql = "SELECT COUNT(*) as count FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'banksystem' AND TABLE_NAME = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, tableName);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            int count = rs.getInt("count");
            return count > 0;
        }
        return false;
    }
    
    /**
     * 获取表的所有列名
     */
    private static List<String> getTableColumns(Connection conn, String tableName) throws SQLException {
        List<String> columns = new ArrayList<>();
        String sql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'banksystem' AND TABLE_NAME = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, tableName);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            columns.add(rs.getString("COLUMN_NAME").toLowerCase());
        }
        
        return columns;
    }
    
    /**
     * 创建user_amount表
     */
    private static boolean createUserAmountTable(Connection conn) {
        try {
            String createTableSQL = "CREATE TABLE `user_amount` (" +
                "`id` BIGINT NOT NULL AUTO_INCREMENT, " +
                "`user_id` BIGINT NOT NULL, " +
                "`amount` BIGINT DEFAULT 0, " +
                "`balance` BIGINT DEFAULT 0, " +
                "`state` INT DEFAULT 1, " +
                "`is_deleted` TINYINT(1) DEFAULT 0, " +
                "`create_by` BIGINT DEFAULT 1, " +
                "`update_by` BIGINT DEFAULT 1, " +
                "`create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "`update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, " +
                "PRIMARY KEY (`id`), " +
                "UNIQUE KEY `uk_user_id` (`user_id`)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
            
            Statement stmt = conn.createStatement();
            stmt.execute(createTableSQL);
            stmt.close();
            
            System.out.println("user_amount表创建成功");
            return true;
        } catch (SQLException e) {
            System.err.println("创建user_amount表失败: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
}