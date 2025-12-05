package com.gzy.mapper;

import com.gzy.entiy.AdminInfo;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AdminMapperImpl implements AdminMapper{

    public static String getCookie(int num) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for(int i = 0; i < num; i++) {
            int index = random.nextInt(str.length());
            sb.append(str.charAt(index));
        }
        return sb.toString();
    }

    @Override
    public void addAdmin(AdminInfo adminInfo) {
        
    }

    @Override
    public AdminInfo queryByMobile(String mobile) {
        return null;
    }

    @Override
    public List<AdminInfo> queryByAll() {
        return Collections.emptyList();
    }

    @Override
    public List<AdminInfo> queryByConditions(String nickname, String name, String mobile) {
        return Collections.emptyList();
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public AdminInfo queryById(Long id) {
        return null;
    }

    @Override
    public void updateById(AdminInfo adminInfo) {

    }
}
