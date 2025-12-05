package com.gzy.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.gzy.dao.UserInfoDao;
import com.gzy.entiy.UserInfo;
import com.gzy.utils.RequestUtil;
import com.gzy.utils.ResponseUtil;
import org.junit.runner.Request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/user/queryByConditions")
public class UserInfoConditionsQueryController extends HttpServlet {
    private UserInfoDao userInfoDao = new UserInfoDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nickname = req.getParameter("nickname");
        String name = req.getParameter("name");
        String mobile = req.getParameter("mobile");
        String cardType = req.getParameter("cardType");
        String cardNo = req.getParameter("cardNo");
        String address = req.getParameter("address");
        String idNum = req.getParameter("idNum");
        String currentPage = req.getParameter("currentPage");
        String pageSize = req.getParameter("pageSize");

        List<UserInfo> userInfos;

        int totalCount;



        // 条件判断逻辑
        boolean hasConditions = (nickname != null && !nickname.trim().isEmpty()) ||
                (name != null && !name.trim().isEmpty()) ||
                (mobile != null && !mobile.trim().isEmpty()) ||
                (cardType != null && !cardType.trim().isEmpty()) ||
                (cardNo != null && !cardNo.trim().isEmpty()) ||
                (address != null && !address.trim().isEmpty()) ||
                (idNum != null && !idNum.trim().isEmpty());

        if (hasConditions) {

            Integer cardTypeValue = null;
            if (cardType != null && !cardType.trim().isEmpty()) {
                try {
                    cardTypeValue = Integer.valueOf(cardType);
                } catch (NumberFormatException e) {
                    // 如果转换失败，则保持为 null，查询时会忽略该条件
                    cardTypeValue = null;
                }
            }
                userInfos = userInfoDao.queryByConditions(nickname, name, mobile, cardTypeValue, cardNo, address, idNum, Integer.parseInt(currentPage), Integer.parseInt(pageSize));;
                totalCount = userInfos.size();
        } else {
                totalCount = userInfoDao.getTotalCount();
                userInfos = userInfoDao.queryByAll(Integer.parseInt(currentPage), Integer.parseInt(pageSize));
            }

            JSONObject result = new JSONObject();
            result.put("userInfos", userInfos);
            result.put("totalCount", totalCount);
            ResponseUtil.success(resp, result);

        }
    }
