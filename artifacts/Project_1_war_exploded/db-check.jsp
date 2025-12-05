<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.gzy.utils.Database" %>
<html>
<head>
    <title>数据库检查</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .success { color: green; }
        .error { color: red; }
        .info { color: blue; }
        table { border-collapse: collapse; margin: 20px 0; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
    </style>
</head>
<body>
<h2>数据库连接和表结构检查</h2>

<%
    try {
        out.println("<h3>1. 数据库连接测试</h3>");
        Connection conn = Database.conn();
        if (conn != null) {
            out.println("<p class='success'>✓ 数据库连接成功</p>");
            
            // 测试user_amount表
            out.println("<h3>2. user_amount表检查</h3>");
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM user_amount");
                if (rs.next()) {
                    int count = rs.getInt("count");
                    out.println("<p class='success'>✓ user_amount表存在，包含 " + count + " 条记录</p>");
                }
                rs.close();
                stmt.close();
                
                // 显示表结构
                out.println("<h3>3. user_amount表结构</h3>");
                stmt = conn.createStatement();
                rs = stmt.executeQuery("DESCRIBE user_amount");
                out.println("<table>");
                out.println("<tr><th>字段名</th><th>类型</th><th>允许NULL</th><th>键</th><th>默认值</th><th>额外</th></tr>");
                
                while (rs.next()) {
                    out.println("<tr>");
                    out.println("<td>" + rs.getString("Field") + "</td>");
                    out.println("<td>" + rs.getString("Type") + "</td>");
                    out.println("<td>" + rs.getString("Null") + "</td>");
                    out.println("<td>" + rs.getString("Key") + "</td>");
                    out.println("<td>" + rs.getString("Default") + "</td>");
                    out.println("<td>" + rs.getString("Extra") + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                out.println("<p class='error'>✗ user_amount表不存在或查询失败: " + e.getMessage() + "</p>");
                // 尝试创建表
                try {
                    out.println("<h3>4. 尝试创建user_amount表</h3>");
                    Statement createStmt = conn.createStatement();
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
                    
                    createStmt.executeUpdate(createTableSQL);
                    out.println("<p class='success'>✓ user_amount表创建成功</p>");
                    createStmt.close();
                } catch (SQLException createException) {
                    out.println("<p class='error'>✗ 创建user_amount表失败: " + createException.getMessage() + "</p>");
                }
            }
            
        } else {
            out.println("<p class='error'>✗ 数据库连接失败</p>");
        }
    } catch (Exception e) {
        out.println("<p class='error'>发生错误: " + e.getMessage() + "</p>");
        e.printStackTrace();
    }
%>

</body>
</html>