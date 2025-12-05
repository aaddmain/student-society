package com.gzy.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.gzy.dao.UserInfoDao;
import com.gzy.entiy.UserInfo;
import com.gzy.utils.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/user/queryAll")
public class UserInfoAllController extends HttpServlet {

    private UserInfoDao userInfoDao = new UserInfoDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentPage = req.getParameter("currentPage");
        String pageSize = req.getParameter("pageSize");

        int totalCount = userInfoDao.getTotalCount();


        List<UserInfo> userInfos = userInfoDao.queryByAll(Integer.parseInt(currentPage), Integer.parseInt(pageSize));

        JSONObject result = new JSONObject();
        result.put("userInfos", userInfos);
        result.put("totalCount", totalCount);
        ResponseUtil.success(resp,result);
    }
}
