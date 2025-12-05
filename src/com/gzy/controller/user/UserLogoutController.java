package com.gzy.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.gzy.utils.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/user/logout")
public class UserLogoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // GET请求也执行登出操作并重定向到登录页面
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 清除session
        HttpSession session = req.getSession();
        session.invalidate();

        // 返回成功响应
        JSONObject result = new JSONObject();
        result.put("message", "退出登录成功");
        ResponseUtil.success(resp, result);
    }
}