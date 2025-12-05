package com.gzy.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableChecker {
    
    /**
     * 检查表是否存在
     * @param tableName 表名
     * @return true表示表存在，false表示表不存在
     */
    public static boolean tableExists(String tableName) {
        Connection conn = null;
        try {
            conn = Database.conn();
            if (conn == null) {
                System.err.println("无法获取数据库连接");
                return false;
            }
            
            // 使用 INFORMATION_SCHEMA 检查表是否存在
            String sql = "SELECT COUNT(*) as count FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'banksystem' AND TABLE_NAME = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tableName);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0;
            }
        } catch (SQLException e) {
            System.err.println("检查表是否存在时发生错误: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * 创建user_amount表
     * @return true表示创建成功，false表示创建失败
     */
    public static boolean createUserAmountTable() {
        Connection conn = null;
        try {
            conn = Database.conn();
            if (conn == null) {
                System.err.println("无法获取数据库连接");
                return false;
            }
            
            String createTableSQL = "CREATE TABLE IF NOT EXISTS `user_amount` (" +
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
            
            PreparedStatement ps = conn.prepareStatement(createTableSQL);
            boolean result = ps.execute();
            ps.close();
            
            System.out.println("user_amount表创建成功");
            return true;
        } catch (SQLException e) {
            System.err.println("创建user_amount表时发生错误: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
}