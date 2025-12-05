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

@WebServlet("/admin/update")
public class AdminInfoByIdUpdateController extends HttpServlet {
    private AdminInfoDao adminInfoDao = new AdminInfoDao();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject param = RequestUtil.getParam(req);
        String id = param.getString("id");
        String nickname = param.getString("nickname");
        String name = param.getString("name");
        String mobile = param.getString("mobile");
        String state = param.getString("state");

        // 检查必要参数是否为空
        if (id == null || id.isEmpty()) {
            ResponseUtil.fail(resp, ErrorCode.ID_FIELD_BLANK);
            return;
        }

        AdminInfo adminInfo = new AdminInfo();

        try {
            adminInfo.setId(Long.parseLong(id));
            adminInfo.setNickname(nickname);
            adminInfo.setName(name);
            adminInfo.setMobile(mobile);

            if (state != null && !state.isEmpty()) {
                adminInfo.setState(Integer.parseInt(state));
            }

            adminInfoDao.updateById(adminInfo);

            JSONObject result = new JSONObject();
            result.put("code", 200);
            result.put("msg", "修改成功");
            ResponseUtil.success(resp, result);
        } catch (NumberFormatException e) {
            ResponseUtil.fail(resp, ErrorCode.PARAM_FORMAT_ERROR);
        }
    }
}
