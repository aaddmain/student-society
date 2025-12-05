package com.gzy.dao;

import com.gzy.entiy.AdminInfo;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class AdminInfoDaoTest extends TestCase {

    AdminInfoDao adminInfoDao = new AdminInfoDao();
//    public void testAddAdmin() {
//        AdminInfo adminInfo = new AdminInfo();
//        adminInfo.setName("李四");
//        adminInfo.setMobile("15824789652");
//        adminInfo.setNickname("lisi");
//        adminInfo.setPassword("123456");
//        adminInfoDao.addAdmin(adminInfo);
//    }

//    public void testQueryByMobile() {
//        AdminInfo adminInfo = new AdminInfo();
//        adminInfo =  adminInfoDao.queryByMobile("13578984120");
//        System.out.println(adminInfo);
//    }

//    public void testQueryById() {
//        AdminInfo adminInfo = new AdminInfo();
//        adminInfo =  adminInfoDao.queryById(2L);
//        System.out.println(adminInfo);
//    }

    public void testQueryByAll() {
        ArrayList<AdminInfo> adminInfos = new ArrayList<AdminInfo>();
        List<AdminInfo> list = adminInfoDao.queryByAll();
        for (AdminInfo info : list) {
            System.out.println(info);
        }
    }

    public void testQueryByConditions() {
        ArrayList<AdminInfo> adminInfos = new ArrayList<AdminInfo>();
        List<AdminInfo> list = adminInfoDao.queryByConditions("lisi",null, "15824789652");
        for (AdminInfo info : list) {
            System.out.println(info);
        }
    }

    public void testUpdateById() {
        AdminInfo adminInfo = new AdminInfo();
        adminInfo.setId(2L);
        adminInfo.setName("lisi");
        adminInfo.setMobile("15824789652");
        adminInfo.setNickname("lisi");
        adminInfo.setState(1);
        adminInfoDao.updateById(adminInfo);
    }

    public void testGetTotalCount() {
        UserInfoDao userInfoDao = new UserInfoDao();
        int totalCount = userInfoDao.getTotalCount();
        System.out.println(totalCount);
    }
}