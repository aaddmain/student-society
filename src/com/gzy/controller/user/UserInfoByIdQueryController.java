package com.gzy.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.gzy.dao.UserInfoDao;
import com.gzy.entiy.UserInfo;
import com.gzy.utils.ErrorCode;
import com.gzy.utils.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/user/queryById")
public class UserInfoByIdQueryController extends HttpServlet {

    private UserInfoDao userInfoDao = new UserInfoDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");

        //判断参数id是否为空
        if (id == null || id.trim().isEmpty()) {
            ResponseUtil.fail(resp, ErrorCode.ID_FIELD_BLANK);
            return;
        }

        UserInfo userInfo = userInfoDao.queryById(Long.parseLong(id));

        JSONObject result = new JSONObject();
        result.put("userInfo", userInfo);
        result.put("success", true);
        result.put("message", "查询成功");
        ResponseUtil.success(resp,result);
    }
}
