package com.gzy.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.gzy.dao.AdminInfoDao;
import com.gzy.entiy.AdminInfo;
import com.gzy.utils.ErrorCode;
import com.gzy.utils.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/queryById")
public class AdminInfoByIdQueryController extends HttpServlet {
    private AdminInfoDao adminInfoDao = new AdminInfoDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        //判断参数
        if (id == null || id.trim().isEmpty()) {
            ResponseUtil.fail(resp, ErrorCode.ID_FIELD_BLANK);
            return;
        }

        AdminInfo adminInfo = adminInfoDao.queryById(Long.parseLong(id));
        JSONObject result = new JSONObject();
        result.put("adminInfo", adminInfo);
        result.put("success", true);
        result.put("message", "查询成功");
        ResponseUtil.success(resp,result);

    }
}
