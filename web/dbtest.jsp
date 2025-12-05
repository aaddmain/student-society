<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.gzy.utils.Database" %>
<html>
<head>
    <title>数据库测试</title>
</head>
<body>
<h2>数据库连接和表结构测试</h2>

<%
    try {
        // 测试数据库连接
        Connection conn = Database.conn();
        if (conn != null) {
            out.println("<p style='color: green;'>数据库连接成功</p>");
            
            // 测试user_amount表是否存在
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("DESCRIBE user_amount");
                out.println("<p style='color: green;'>user_amount表存在</p>");
                out.println("<h3>user_amount表结构：</h3>");
                out.println("<table border='1' style='border-collapse: collapse;'>");
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
                out.println("<p style='color: red;'>user_amount表不存在或查询失败: " + e.getMessage() + "</p>");
            }
            
            // 测试查询数据
            try {
                PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM user_amount");
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int count = rs.getInt(1);
                    out.println("<p>user_amount表中有 " + count + " 条记录</p>");
                }
                rs.close();
                ps.close();
            } catch (SQLException e) {
                out.println("<p style='color: red;'>查询数据失败: " + e.getMessage() + "</p>");
            }
            
        } else {
            out.println("<p style='color: red;'>数据库连接失败</p>");
        }
    } catch (Exception e) {
        out.println("<p style='color: red;'>发生错误: " + e.getMessage() + "</p>");
        e.printStackTrace();
    }
%>

</body>
</html>