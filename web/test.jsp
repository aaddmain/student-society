<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>测试页面</title>
</head>
<body>
<h1>应用程序部署测试</h1>
<p>如果能看到这个页面，说明应用程序已正确部署。</p>
<p>上下文路径: <%= request.getContextPath() %></p>
<p>服务器信息: <%= application.getServerInfo() %></p>
</body>
</html>