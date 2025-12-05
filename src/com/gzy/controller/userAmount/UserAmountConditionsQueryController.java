package com.gzy.controller.userAmount;

import com.alibaba.fastjson.JSONObject;
import com.gzy.dao.UserAmountDao;
import com.gzy.entiy.UserAmount;
import com.gzy.utils.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/userAmount/queryByConditions")
public class UserAmountConditionsQueryController extends HttpServlet {

    private UserAmountDao userAmountDao = new UserAmountDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nicknameAmount = req.getParameter("nicknameAmount");
        String nameAmount = req.getParameter("nameAmount");
        String currentPage = req.getParameter("currentPage");
        String pageSize = req.getParameter("pageSize");

        int totalCount;
        List<UserAmount> userAmounts;

        //逻辑条件判断
        boolean hasConditions = (nicknameAmount != null && !nicknameAmount.trim().isEmpty()) ||
                (nameAmount != null && !nameAmount.trim().isEmpty());

        if (hasConditions) {
            userAmounts = userAmountDao.queryByConditions(nicknameAmount, nameAmount, Integer.parseInt(currentPage), Integer.parseInt(pageSize));

            totalCount = userAmounts.size();
        }else {
            totalCount = userAmountDao.getTotalCount();
            userAmounts = userAmountDao.getAllUserAmount(Integer.parseInt(currentPage), Integer.parseInt(pageSize));
        }

        JSONObject result = new JSONObject();
        result.put("userAmounts", userAmounts);
        result.put("totalCount", totalCount);
        ResponseUtil.success(resp, result);
    }
}
