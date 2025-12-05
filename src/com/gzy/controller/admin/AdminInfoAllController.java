package com.gzy.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.gzy.dao.AdminInfoDao;
import com.gzy.entiy.AdminInfo;
import com.gzy.utils.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/queryAll")
public class AdminInfoAllController extends HttpServlet {
    private AdminInfoDao adminInfoDao = new AdminInfoDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<AdminInfo> adminInfos = adminInfoDao.queryByAll();

        JSONObject result = new JSONObject();
        result.put("adminInfos", adminInfos);
        ResponseUtil.success(resp,result);
    }


}
