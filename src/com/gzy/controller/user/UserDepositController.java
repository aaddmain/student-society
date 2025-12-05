package com.gzy.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.gzy.dao.UserAmountDao;
import com.gzy.dao.UserInfoDao;
import com.gzy.entiy.UserAmount;
import com.gzy.entiy.UserInfo;
import com.gzy.utils.Database;
import com.gzy.utils.DatabaseFixer;
import com.gzy.utils.ErrorCode;
import com.gzy.utils.ResponseUtil;
import com.gzy.utils.TableChecker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Enumeration;

@WebServlet("/user/deposit")
public class UserDepositController extends HttpServlet {
    private UserAmountDao userAmountDao = new UserAmountDao();
    private UserInfoDao userInfoDao = new UserInfoDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("=== 开始处理存款请求 ===");
        
        // 检查并修复数据库表结构
        if (!DatabaseFixer.fixUserAmountTable()) {
            System.out.println("修复user_amount表失败");
            ResponseUtil.fail(resp, ErrorCode.SYSTEM_ERROR);
            return;
        }
        
        // 检查用户是否已登录
        HttpSession session = req.getSession();
        UserInfo currentUser = (UserInfo) session.getAttribute("userInfo");
        
        if (currentUser == null) {
            System.out.println("用户未登录");
            ResponseUtil.fail(resp, ErrorCode.USER_NOT_LOGIN);
            return;
        }
        
        System.out.println("当前用户ID: " + currentUser.getId());

        // 打印所有参数用于调试
        System.out.println("=== 所有请求参数 ===");
        Enumeration<String> paramNames = req.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String paramValue = req.getParameter(paramName);
            System.out.println("参数: " + paramName + " = " + paramValue);
        }
        System.out.println("=== 参数结束 ===");

        // 获取参数
        String amountStr = req.getParameter("amount");
        String password = req.getParameter("password");

        System.out.println("接收到的参数 - 金额: " + amountStr + ", 密码长度: " + (password != null ? password.length() : "null"));

        // 参数校验
        if (amountStr == null || amountStr.trim().isEmpty()) {
            System.out.println("金额参数为空或空字符串");
            ResponseUtil.fail(resp, ErrorCode.PARAM_FORMAT_ERROR);
            return;
        }

        if (password == null || password.trim().isEmpty()) {
            System.out.println("密码参数为空或空字符串");
            ResponseUtil.fail(resp, ErrorCode.PWD_FIELD_BLANK);
            return;
        }

        try {
            System.out.println("开始验证用户密码");
            // 验证密码
            UserInfo userInfo = userInfoDao.queryById(currentUser.getId());
            if (userInfo == null) {
                System.out.println("未找到用户信息，用户ID: " + currentUser.getId());
                ResponseUtil.fail(resp, ErrorCode.SYSTEM_ERROR);
                return;
            }
            
            if (!password.equals(userInfo.getPassword())) {
                System.out.println("密码验证失败");
                ResponseUtil.fail(resp, ErrorCode.USER_PWD_ERROR);
                return;
            }
            System.out.println("密码验证成功");

            // 转换金额（分）
            System.out.println("开始转换金额");
            double amountDouble;
            try {
                amountDouble = Double.parseDouble(amountStr);
                System.out.println("解析后的金额: " + amountDouble);
            } catch (NumberFormatException e) {
                System.out.println("金额格式错误: " + amountStr);
                e.printStackTrace();
                ResponseUtil.fail(resp, ErrorCode.PARAM_FORMAT_ERROR);
                return;
            }
            
            long amount = Math.round(amountDouble * 100);
            System.out.println("转换为分为单位的金额: " + amount);
            if (amount <= 0) {
                System.out.println("金额小于等于零: " + amount);
                ResponseUtil.fail(resp, ErrorCode.PARAM_FORMAT_ERROR);
                return;
            }

            // 开启事务
            System.out.println("开始事务");
            Database.beginTransaction();

            // 查询用户账户信息
            System.out.println("查询用户账户信息，用户ID: " + currentUser.getId());
            UserAmount userAmount = userAmountDao.queryByUserId(currentUser.getId());
            
            // 如果用户没有账户，则创建一个
            if (userAmount == null) {
                System.out.println("用户没有账户，创建新账户，用户ID: " + currentUser.getId());
                try {
                    boolean addResult = userAmountDao.add(currentUser.getId(), 1); // 创建状态为1(启用)的账户
                    if (!addResult) {
                        System.out.println("创建账户失败，用户ID: " + currentUser.getId());
                        ResponseUtil.fail(resp, ErrorCode.SYSTEM_ERROR);
                        Database.rollbackTransaction();
                        return;
                    }
                    
                    userAmount = userAmountDao.queryByUserId(currentUser.getId()); // 重新查询
                    
                    if (userAmount == null) {
                        System.out.println("创建账户后查询失败，用户ID: " + currentUser.getId());
                        ResponseUtil.fail(resp, ErrorCode.SYSTEM_ERROR);
                        Database.rollbackTransaction();
                        return;
                    }
                    System.out.println("账户创建成功");
                } catch (Exception e) {
                    System.out.println("创建账户时发生异常: " + e.getMessage());
                    e.printStackTrace();
                    ResponseUtil.fail(resp, ErrorCode.SYSTEM_ERROR);
                    Database.rollbackTransaction();
                    return;
                }
            }
            
            System.out.println("当前用户余额: " + userAmount.getBalance());

            // 更新余额
            long newBalance = userAmount.getBalance() + amount;
            userAmount.setBalance(newBalance);
            userAmount.setAmount(amount);
            
            System.out.println("新余额: " + newBalance);

            // 更新数据库
            String updateSql = "UPDATE user_amount SET balance = ?, amount = ?, update_time = CURRENT_TIMESTAMP() WHERE user_id = ?";
            System.out.println("执行SQL: " + updateSql);
            
            Connection conn = Database.conn();
            if (conn == null) {
                System.out.println("数据库连接为空");
                ResponseUtil.fail(resp, ErrorCode.SYSTEM_ERROR);
                Database.rollbackTransaction();
                return;
            }
            
            System.out.println("准备执行更新");
            PreparedStatement ps = conn.prepareStatement(updateSql);
            ps.setLong(1, newBalance);
            ps.setLong(2, amount);
            ps.setLong(3, currentUser.getId());
            int result = ps.executeUpdate();
            
            System.out.println("SQL更新结果: " + result);

            if (result > 0) {
                // 提交事务
                System.out.println("提交事务");
                Database.commitTransaction();

                JSONObject resultObj = new JSONObject();
                resultObj.put("success", true);
                resultObj.put("message", "存款成功");
                resultObj.put("newBalance", newBalance / 100.0); // 转换为元
                ResponseUtil.success(resp, resultObj);
                System.out.println("存款成功，返回结果");
            } else {
                System.out.println("SQL更新失败，回滚事务");
                Database.rollbackTransaction();
                ResponseUtil.fail(resp, ErrorCode.SYSTEM_ERROR);
            }
        } catch (Exception e) {
            // 回滚事务
            System.out.println("发生异常，回滚事务: " + e.getMessage());
            e.printStackTrace();
            
            // 打印完整的堆栈跟踪到日志
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            System.out.println("完整堆栈跟踪: " + sw.toString());
            
            try {
                Database.rollbackTransaction();
            } catch (Exception rollbackException) {
                System.out.println("回滚事务时发生异常: " + rollbackException.getMessage());
                rollbackException.printStackTrace();
            }
            ResponseUtil.fail(resp, ErrorCode.SYSTEM_ERROR);
        }
        
        System.out.println("=== 存款请求处理结束 ===");
    }
}