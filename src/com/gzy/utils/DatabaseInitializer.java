package com.gzy.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    
    /**
     * 执行SQL脚本文件
     * @param scriptFilePath SQL脚本文件路径
     */
    public static void executeSqlScript(String scriptFilePath) {
        Connection conn = null;
        Statement stmt = null;
        BufferedReader reader = null;
        
        try {
            conn = Database.conn();
            if (conn == null) {
                System.err.println("无法获取数据库连接");
                return;
            }
            
            stmt = conn.createStatement();
            reader = new BufferedReader(new FileReader(scriptFilePath));
            
            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                // 跳过注释和空行
                if (line.trim().isEmpty() || line.trim().startsWith("--")) {
                    continue;
                }
                
                sql.append(line).append(" ");
                
                // 如果遇到分号，说明一条SQL语句结束了
                if (line.trim().endsWith(";")) {
                    String sqlStatement = sql.toString().trim();
                    // 移除最后的分号
                    if (sqlStatement.endsWith(";")) {
                        sqlStatement = sqlStatement.substring(0, sqlStatement.length() - 1);
                    }
                    
                    if (!sqlStatement.isEmpty()) {
                        System.out.println("执行SQL: " + sqlStatement);
                        try {
                            stmt.execute(sqlStatement);
                            System.out.println("执行成功");
                        } catch (SQLException e) {
                            System.err.println("执行SQL失败: " + e.getMessage());
                            // 不中断执行，继续下一条SQL
                        }
                    }
                    
                    // 重置StringBuilder
                    sql.setLength(0);
                }
            }
            
            System.out.println("SQL脚本执行完成");
        } catch (IOException e) {
            System.err.println("读取SQL脚本文件失败: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("数据库操作失败: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            // 注意：这里不关闭连接，因为Database类管理连接
        }
    }
}