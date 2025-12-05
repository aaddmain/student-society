<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.gzy.utils.Database" %>
<html>
<head>
    <title>初始化数据库</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .success { color: green; }
        .error { color: red; }
        .info { color: blue; }
        textarea { width: 100%; height: 200px; }
    </style>
</head>
<body>
<h2>初始化数据库</h2>

<%
    if ("POST".equals(request.getMethod())) {
        String sqlScript = request.getParameter("sql");
        if (sqlScript != null && !sqlScript.trim().isEmpty()) {
            Connection conn = null;
            try {
                conn = Database.conn();
                if (conn != null) {
                    // 分割SQL语句
                    String[] statements = sqlScript.split(";");
                    for (String sql : statements) {
                        sql = sql.trim();
                        if (!sql.isEmpty()) {
                            try {
                                PreparedStatement ps = conn.prepareStatement(sql);
                                boolean hasResultSet = ps.execute();
                                out.println("<p class='success'>执行成功: " + sql + "</p>");
                                ps.close();
                            } catch (SQLException e) {
                                out.println("<p class='error'>执行失败: " + sql + "<br>错误: " + e.getMessage() + "</p>");
                            }
                        }
                    }
                } else {
                    out.println("<p class='error'>数据库连接失败</p>");
                }
            } catch (Exception e) {
                out.println("<p class='error'>发生错误: " + e.getMessage() + "</p>");
                e.printStackTrace();
            }
        }
    }
%>

<form method="post">
    <h3>执行SQL脚本</h3>
    <textarea name="sql" placeholder="输入SQL语句，多个语句用分号分隔">CREATE TABLE IF NOT EXISTS `user_amount` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `amount` BIGINT DEFAULT 0,
  `balance` BIGINT DEFAULT 0,
  `state` INT DEFAULT 1,
  `is_deleted` TINYINT(1) DEFAULT 0,
  `create_by` BIGINT DEFAULT 1,
  `update_by` BIGINT DEFAULT 1,
  `create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;</textarea>
    <br><br>
    <input type="submit" value="执行SQL">
</form>

</body>
</html>