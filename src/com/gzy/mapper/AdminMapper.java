package com.gzy.mapper;

import com.gzy.entiy.AdminInfo;

import java.util.List;
import java.util.Random;

public interface AdminMapper {

    AdminInfo queryByMobile(String mobile);
    List<AdminInfo> queryByAll();
    List<AdminInfo> queryByConditions(String nickname, String name, String mobile);
    void deleteById(Long id);
    AdminInfo queryById(Long id);
    void updateById(AdminInfo adminInfo);
    void addAdmin(AdminInfo adminInfo);



}
