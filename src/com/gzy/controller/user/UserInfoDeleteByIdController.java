package com.gzy.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.gzy.dao.UserAmountDao;
import com.gzy.dao.UserInfoDao;
import com.gzy.utils.Database;
import com.gzy.utils.ErrorCode;
import com.gzy.utils.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/deleteById")
public class UserInfoDeleteByIdController extends HttpServlet {

    private UserInfoDao userInfoDao = new UserInfoDao();

    private UserAmountDao userAmountDao = new UserAmountDao();

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");

        // 参数校验
        if (id == null || id.trim().isEmpty()) {
            ResponseUtil.fail(resp, ErrorCode.ID_FIELD_BLANK);
            return;
        }

        try {
            //开启事务
            Database.beginTransaction();

            userInfoDao.deleteById(Long.parseLong(id));
            userAmountDao.deleteById(Long.parseLong(id));

            //提交事务
            Database.commitTransaction();


            JSONObject result = new JSONObject();
            result.put("success", true);
            result.put("message", "删除成功");
            ResponseUtil.success(resp,result);
        } catch (Exception e) {
            //回滚事务
            Database.rollbackTransaction();
            e.printStackTrace();
            ResponseUtil.fail(resp, ErrorCode.SYSTEM_ERROR);
        }


    }
}
