package com.gzy.controller;

import com.alibaba.fastjson.JSONObject;
import com.gzy.utils.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/test-login")
public class TestLoginServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 测试响应
        JSONObject json = new JSONObject();
        json.put("message", "Test login endpoint is working");
        json.put("success", true);
        ResponseUtil.success(resp, json);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 测试响应
        JSONObject json = new JSONObject();
        json.put("message", "Test login POST endpoint is working");
        json.put("success", true);
        json.put("receivedData", req.getParameterMap().toString());
        ResponseUtil.success(resp, json);
    }
}