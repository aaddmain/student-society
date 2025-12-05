package com.gzy.controller;


import com.alibaba.fastjson.JSONObject;
import com.gzy.dao.AdminInfoDao;
import com.gzy.entiy.AdminInfo;
import com.gzy.utils.ErrorCode;
import com.gzy.utils.RequestUtil;
import com.gzy.utils.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;


@WebServlet("/login")
public class TestController extends HttpServlet {
    private AdminInfoDao adminInfoDao = new AdminInfoDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // GET请求重定向到登录页面
        resp.sendRedirect(req.getContextPath() + "/login-register.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 确保响应内容类型为JSON
        resp.setContentType("application/json;charset=utf-8");
        
        try {
            System.out.println("Login request received at: " + System.currentTimeMillis());
            
            JSONObject jsonObject = RequestUtil.getParam(req);
            System.out.println("Received login request: " + jsonObject);
            
            if (jsonObject == null) {
                System.err.println("Request body is null or invalid");
                ResponseUtil.fail(resp, ErrorCode.PARAM_FORMAT_ERROR);
                return;
            }
            
            String mobile = jsonObject.getString("mobile");
            String pwd = jsonObject.getString("pwd");
            String loginType = jsonObject.getString("loginType");
            
            System.out.println("Login parameters - mobile: " + mobile + ", loginType: " + loginType);
            
            if (mobile == null || pwd == null || loginType == null) {
                System.err.println("Missing required parameters: mobile=" + mobile + ", pwd=" + pwd + ", loginType=" + loginType);
                ResponseUtil.fail(resp, ErrorCode.PARAM_FORMAT_ERROR);
                return;
            }
            
            if ("admin".equals(loginType)) {
                System.out.println("Processing admin login for mobile: " + mobile);
                AdminInfo adminInfo = adminInfoDao.queryByMobile(mobile);
                // 1. 判断是否存在admin
                if(Objects.isNull(adminInfo)) {
                    System.err.println("Admin not found for mobile: " + mobile);
                    ResponseUtil.fail(resp, ErrorCode.ADMIN_NOT_EXIST);
                    return;
                }
                // 2. 判断密码是否正确
                if(!adminInfo.getPassword().equals(pwd)) {
                    System.err.println("Invalid password for admin: " + mobile);
                    ResponseUtil.fail(resp, ErrorCode.PWD_ERR);
                    return;
                }
                System.out.println("Admin login successful: " + adminInfo);
                
                // 将管理员信息存储到会话中
                HttpSession session = req.getSession();
                session.setAttribute("adminInfo", adminInfo);
                
                JSONObject json = new JSONObject();
                json.put("adminInfo", adminInfo);
                json.put("success", true);
                json.put("message", "登录成功");
                ResponseUtil.success(resp, json);
                return;
            }
            
            // 如果不是管理员登录类型
            System.err.println("Invalid login type: " + loginType);
            ResponseUtil.fail(resp, ErrorCode.NOT_LOGIN);
        } catch (Exception e) {
            System.err.println("Exception during login processing:");
            e.printStackTrace();
            
            // 发生异常时返回系统错误
            try {
                ResponseUtil.fail(resp, ErrorCode.SYSTEM_ERROR);
            } catch (Exception ex) {
                System.err.println("Failed to send error response:");
                ex.printStackTrace();
                
                // 最后的备用错误响应
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                PrintWriter out = resp.getWriter();
                out.print("{\"success\":false,\"message\":\"系统错误\"}");
                out.flush();
            }
        }
    }
}