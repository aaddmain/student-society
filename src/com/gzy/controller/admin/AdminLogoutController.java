package com.gzy.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.gzy.utils.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/admin/logout")
public class AdminLogoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 清除会话
        HttpSession session = req.getSession();
        session.invalidate();

        // 返回成功响应
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("message", "退出登录成功");
        ResponseUtil.success(resp, result);
    }
}