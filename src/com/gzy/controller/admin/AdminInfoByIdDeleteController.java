package com.gzy.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.gzy.dao.AdminInfoDao;
import com.gzy.utils.ErrorCode;
import com.gzy.utils.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/delete")
public class AdminInfoByIdDeleteController extends HttpServlet {
    private AdminInfoDao adminInfoDao = new AdminInfoDao();

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        // 参数校验
        if (id == null || id.trim().isEmpty()) {
            ResponseUtil.fail(resp, ErrorCode.ID_FIELD_BLANK);
            return;
        }

        adminInfoDao.deleteById(Long.parseLong(id));

        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("message", "删除成功");
        ResponseUtil.success(resp,result);
    }
}
