package com.gzy.filter;

import com.alibaba.fastjson.JSONObject;
import com.gzy.entiy.AdminInfo;
import com.gzy.utils.ErrorCode;
import com.gzy.utils.ResponseUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/admin/*")
public class AdminLoginFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化操作
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
        // 获取会话
        HttpSession session = req.getSession();
        
        // 检查会话中是否有管理员信息
        AdminInfo adminInfo = (AdminInfo) session.getAttribute("adminInfo");
        
        // 如果没有管理员信息，检查是否是登录请求
        if (adminInfo == null) {
            // 获取请求URI
            String requestURI = req.getRequestURI();
            String contextPath = req.getContextPath();
            
            // 如果是登录页面或登录请求，允许通过
            if (requestURI.endsWith("/admin-login.html") || 
                requestURI.endsWith("/admin-login.jsp") ||
                requestURI.equals(contextPath + "/login")) {
                chain.doFilter(request, response);
                return;
            }
            
            // AJAX请求返回JSON错误信息
            String requestedWith = req.getHeader("X-Requested-With");
            if ("XMLHttpRequest".equals(requestedWith)) {
                ResponseUtil.fail(resp, ErrorCode.ADMIN_NOT_LOGIN);
                return;
            }
            
            // 普通请求重定向到登录页面
            resp.sendRedirect(contextPath + "/admin-login.html");
            return;
        }
        
        // 如果已登录，允许访问
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 清理操作
    }
}