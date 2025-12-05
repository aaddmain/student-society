package com.gzy.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.gzy.dao.UserAmountDao;
import com.gzy.dao.UserInfoDao;
import com.gzy.entiy.UserInfo;
import com.gzy.utils.Database;
import com.gzy.utils.ErrorCode;
import com.gzy.utils.RequestUtil;
import com.gzy.utils.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/user/update")
public class UserInfoUpdateController extends HttpServlet {

    private UserInfoDao userInfoDao = new UserInfoDao();

    private UserAmountDao userAmountDao = new UserAmountDao();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject param = RequestUtil.getParam(req);
        String id = param.getString("id");
        String nickname = param.getString("nickname");
        String name = param.getString("name");
        String mobile = param.getString("mobile");
        String cardType = param.getString("cardType");
        String cardNo = param.getString("cardNo");
        String address = param.getString("address");
        String idNum = param.getString("idNum");
        String state = param.getString("state");

        // 检查必要参数是否为空
        if (id == null || id.isEmpty()) {
            ResponseUtil.fail(resp, ErrorCode.ID_FIELD_BLANK);
            return;
        }

        try {
            //开启事务
            Database.beginTransaction();

            UserInfo userInfo = new UserInfo();

            userInfo.setId(Long.parseLong(id));
            userInfo.setNickname(nickname);
            userInfo.setName(name);
            userInfo.setMobile(mobile);
            userInfo.setCardType(cardType);
            userInfo.setCardNo(cardNo);
            userInfo.setAddress(address);
            userInfo.setIdNum(idNum);
            userInfo.setState(Integer.parseInt(state));

            userInfoDao.updateById(userInfo);

            if (userInfo.getState() == 1) {
                userAmountDao.updateByUserId(userInfo.getId(), userInfo.getState());
            } else if (userInfo.getState() == 0 || userInfo.getState() == 2) {
                userAmountDao.updateByUserId(userInfo.getId(), 0);
            }
            // 提交事务
            Database.commitTransaction();

            JSONObject result = new JSONObject();
            result.put("code", 200);
            ResponseUtil.success(resp, result);
        } catch (Exception e) {
            // 回滚事务
            Database.rollbackTransaction();
            e.printStackTrace();
            ResponseUtil.fail(resp, ErrorCode.UNKNOWN_ERROR);
        }
    }
}
