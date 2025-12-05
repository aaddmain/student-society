package com.gzy.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.gzy.dao.UserInfoDao;
import com.gzy.entiy.UserInfo;
import com.gzy.utils.ErrorCode;
import com.gzy.utils.RequestUtil;
import com.gzy.utils.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Objects;


@WebServlet("/user/login")
public class UserLoginController extends HttpServlet {
    private UserInfoDao userInfoDao = new UserInfoDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // GET请求重定向到登录页面
        resp.sendRedirect(req.getContextPath() + "/login-register.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            JSONObject jsonObject = RequestUtil.getParam(req);
            String nickname = jsonObject.getString("nickname");
            String cardNo = jsonObject.getString("cardNo");
            String password = jsonObject.getString("password");

            // 参数校验
            if (nickname == null || nickname.isEmpty()) {
                ResponseUtil.fail(resp, ErrorCode.NICKNAME_FIELD_BLANK);
                return;
            }

            if (cardNo == null || cardNo.isEmpty()) {
                ResponseUtil.fail(resp, ErrorCode.CARD_NO_FIELD_BLANK);
                return;
            }

            if (password == null || password.isEmpty()) {
                ResponseUtil.fail(resp, ErrorCode.PWD_FIELD_BLANK);
                return;
            }

            // 查询用户信息
            UserInfo userInfo = getUserInfoByNicknameAndCardNo(nickname, cardNo);

            // 判断用户是否存在
            if (Objects.isNull(userInfo)) {
                ResponseUtil.fail(resp, ErrorCode.USER_NOT_EXIST);
                return;
            }

            // 判断密码是否正确
            if (!userInfo.getPassword().equals(password)) {
                ResponseUtil.fail(resp, ErrorCode.PWD_ERR);
                return;
            }

            // 判断用户状态
            if (userInfo.getState() == 0) {
                ResponseUtil.fail(resp, ErrorCode.USER_ACCOUNT_DISABLED);
                return;
            }

            // 登录成功，将用户信息存储到session中
            HttpSession session = req.getSession();
            session.setAttribute("userInfo", userInfo);

            JSONObject result = new JSONObject();
            result.put("userInfo", userInfo);
            ResponseUtil.success(resp, result);
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            ResponseUtil.fail(resp, ErrorCode.SYSTEM_ERROR);
        }
    }

    /**
     * 根据用户名和卡号查询用户信息
     *
     * @param nickname 用户名
     * @param cardNo   卡号
     * @return 用户信息
     */
    private UserInfo getUserInfoByNicknameAndCardNo(String nickname, String cardNo) {
        try {
            // 使用专门的方法进行精确匹配查询
            return userInfoDao.queryByNicknameAndCardNo(nickname, cardNo);
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            throw e;
        }
    }
}