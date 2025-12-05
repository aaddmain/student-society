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


@WebServlet("/user/add")
public class UserInfoAddController extends HttpServlet {

    private UserInfoDao userInfoDao = new UserInfoDao();

    private UserAmountDao userAmountDao = new UserAmountDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject param = RequestUtil.getParam(req);

        String nickname = param.getString("nickname");
        String name = param.getString("name");
        String mobile = param.getString("mobile");
        String cardType = param.getString("cardType");
        String cardNo = param.getString("cardNo");
        String address = param.getString("address");
        String idNum = param.getString("idNum");
        String stateStr = param.getString("state");
        String password = param.getString("password");

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

        if (cardType == null || cardType.isEmpty()) {
            ResponseUtil.fail(resp, ErrorCode.CARD_TYPE_FIELD_BLANK);
            return;
        }

        if (cardNo == null || cardNo.isEmpty()) {
            ResponseUtil.fail(resp, ErrorCode.CARD_NO_FIELD_BLANK);
            return;
        }

        if (address == null || address.isEmpty()) {
            ResponseUtil.fail(resp, ErrorCode.ADDRESS_FIELD_BLANK);
            return;
        }

        if (idNum == null || idNum.isEmpty()) {
            ResponseUtil.fail(resp, ErrorCode.ID_NUM_FIELD_BLANK);
            return;
        }

        if (password == null || password.isEmpty()) {
            ResponseUtil.fail(resp, ErrorCode.PWD_FIELD_BLANK);
            return;
        }

        // 检查是否已存在相同的昵称
        if (userInfoDao.existsByNickname(nickname)) {
            ResponseUtil.fail(resp, ErrorCode.USER_MOBILE_SAME);
            return;
        }

        // 检查是否已存在相同的卡号
        if (userInfoDao.existsByCardNo(cardNo)) {
            ResponseUtil.fail(resp, ErrorCode.USER_CARDNO_OR_IDNUM_SAME);
            return;
        }

        // 检查是否已存在相同的身份证号
        if (userInfoDao.existsByIdNum(idNum)) {
            ResponseUtil.fail(resp, ErrorCode.USER_CARDNO_OR_IDNUM_SAME);
            return;
        }

        try {
            //开启事务
            Database.beginTransaction();

            UserInfo userInfo = new UserInfo();
            Integer state = Integer.parseInt(stateStr);

            userInfo.setNickname(nickname);
            userInfo.setName(name);
            userInfo.setMobile(mobile);
            userInfo.setCardType(cardType);
            userInfo.setCardNo(cardNo);
            userInfo.setAddress(address);
            userInfo.setIdNum(idNum);
            userInfo.setState(state);
            // 使用用户输入的密码
            userInfo.setPassword(param.getString("password"));

            //添加用户信息
            Long userId = userInfoDao.add(userInfo);

            //根据用户状态添加账户信息
            if (state == 1) {
                userAmountDao.add(userId, state);
            } else if (state == 0 || state == 2) {
                state = 0;
                userAmountDao.add(userId, state);
            }


            //提交事务
            Database.commitTransaction();

            JSONObject result = new JSONObject();
            result.put("code", 200);
            result.put("msg", "添加成功");
            ResponseUtil.success(resp, result);
        } catch (Exception e) {
            //发生异常时回滚事务
            Database.rollbackTransaction();
            e.printStackTrace();
            ResponseUtil.fail(resp, ErrorCode.SYSTEM_ERROR);
        }
    }
}