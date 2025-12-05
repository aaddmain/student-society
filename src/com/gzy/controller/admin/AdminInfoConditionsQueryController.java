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

@WebServlet("/admin/conditionsQuery")
public class AdminInfoConditionsQueryController extends HttpServlet {

    private AdminInfoDao adminInfoDao = new AdminInfoDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String nickname = req.getParameter("nickname");
        String name = req.getParameter("name");
        String mobile = req.getParameter("mobile");

        List<AdminInfo> adminInfos;

        //满足任意一个查询条件，则使用条件进行查询，否则查询全部数据
        if ((nickname != null && !nickname.trim().isEmpty()) ||
            (name != null && !name.trim().isEmpty()) ||
            (mobile != null && !mobile.trim().isEmpty())) {
            adminInfos = adminInfoDao.queryByConditions(nickname, name, mobile);
        } else {
            adminInfos = adminInfoDao.queryByAll();
        }

        JSONObject result = new JSONObject();
        result.put("adminInfos", adminInfos);
        ResponseUtil.success(resp,result);
    }
}
