package com.gzy.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.gzy.dao.AdminInfoDao;
import com.gzy.entiy.AdminInfo;
import com.gzy.utils.ErrorCode;
import com.gzy.utils.RequestUtil;
import com.gzy.utils.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/add")
public class AdminInfoAddController extends HttpServlet {
    private AdminInfoDao adminInfoDao = new AdminInfoDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject param = RequestUtil.getParam(req);
        String nickname = param.getString("nickname");
        String name = param.getString("name");
        String mobile = param.getString("mobile");
        String state = param.getString("state");

        if (nickname == null || nickname.isEmpty()) {
            ResponseUtil.fail(resp, ErrorCode.NICKNAME_FIELD_BLANK);
            return;
        }

        if (name == null || name.isEmpty()) {
            ResponseUtil.fail(resp, ErrorCode.NAME_FIELD_BLANK);
            return;
        }
        if (mobile == null || mobile.isEmpty()) {
            ResponseUtil.fail(resp, ErrorCode.MOBILE_FIELD_BLANK);
            return;
        }



        AdminInfo adminInfo = new AdminInfo();

        adminInfo.setNickname(nickname);
        adminInfo.setName(name);
        adminInfo.setMobile(mobile);
        adminInfo.setState(state == null ? 0 : Integer.parseInt(state));

        adminInfoDao.addAdmin(adminInfo);

        JSONObject result = new JSONObject();
        result.put("code", 200);
        result.put("msg", "添加成功");
        ResponseUtil.success(resp, result);
    }
}
