<%--
  Created by IntelliJ IDEA.
  User: AI PC
  Date: 2025/9/12
  Time: 上午9:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // 检查用户是否已经登录
    Object userInfo = session.getAttribute("userInfo");
    if (userInfo == null) {
        // 用户未登录，重定向到登录页面
        response.sendRedirect(request.getContextPath() + "/login-register.html");
        return;
    }
    
    // 用户已登录，重定向到用户主页
    response.sendRedirect(request.getContextPath() + "/user-home.jsp");
    return;
%>