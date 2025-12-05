package com.gzy.dao;

import com.gzy.entiy.AdminInfo;
import com.gzy.utils.ConvertUtils;
import com.gzy.utils.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AdminInfoDao {

    /**
     * 根据手机号查询
     * @param mobile
     * @return
     */
    public AdminInfo queryByMobile(String mobile) {
        Connection conn = Database.conn();
        String sql = "select id,password,nickname,name,mobile,state,is_deleted,create_by,update_by,create_time,update_time  from admin_info where mobile = ?";
        AdminInfo adminInfo = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,mobile);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                adminInfo = new AdminInfo();
                adminInfo.setId(rs.getLong("id"));
                adminInfo.setPassword(rs.getString("password"));
                adminInfo.setNickname(rs.getString("nickname"));
                adminInfo.setName(rs.getString("name"));
                adminInfo.setMobile(rs.getString("mobile"));
                adminInfo.setState(rs.getInt("state"));
                adminInfo.setIsDelete(rs.getBoolean("is_deleted"));
                adminInfo.setCreateBy(rs.getLong("create_by"));
                adminInfo.setUpdateBy(rs.getLong("update_by"));
//                adminInfo.setCreateTime(ConvertUtils.toInstant(rs.getString("create_time")));
//                adminInfo.setUpdateTime(ConvertUtils.toInstant(rs.getString("update_time")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return adminInfo;
    }



    //查询全部管理员信息
    public List<AdminInfo> queryByAll() {
        String sql = "select * from admin_info where is_deleted = 0";
        ArrayList<AdminInfo> list = new ArrayList<>();
        try {
            Connection conn = Database.conn();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AdminInfo adminInfo = new AdminInfo();
                adminInfo.setId(rs.getLong("id"));
                adminInfo.setPassword(rs.getString("password"));
                adminInfo.setNickname(rs.getString("nickname"));
                adminInfo.setName(rs.getString("name"));
                adminInfo.setState(rs.getInt("state"));
                adminInfo.setIsDelete(rs.getBoolean("is_deleted"));
                adminInfo.setMobile(rs.getString("mobile"));
                adminInfo.setCreateBy(rs.getLong("create_by"));
                adminInfo.setUpdateBy(rs.getLong("update_by"));
                adminInfo.setCreateTime(ConvertUtils.toInstant(rs.getString("create_time")));
                adminInfo.setUpdateTime(ConvertUtils.toInstant(rs.getString("update_time")));
                list.add(adminInfo);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    //根据条件查询(用户名，姓名，手机号)
    public List<AdminInfo> queryByConditions(String nickname, String name, String mobile) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select * from admin_info where is_deleted = 0");

        //创建参数列表
        List<String> params = new ArrayList<>();

        //动态构建sql语句
        if (nickname != null && !nickname.trim().isEmpty()) {
            sqlBuilder.append(" and nickname like ?");
            params.add("%" + nickname + "%");
        }

        if (name != null && !name.trim().isEmpty()) {
            sqlBuilder.append(" and name like ?");
            params.add("%" + name + "%");
        }

        if (mobile != null && !mobile.trim().isEmpty()) {
            sqlBuilder.append(" and mobile = ?");
            params.add(mobile);
        }

        String sql = sqlBuilder.toString();
        ArrayList<AdminInfo> list = new ArrayList<>();

        try {
            Connection conn = Database.conn();
            PreparedStatement ps = conn.prepareStatement(sql);

            //设置参数
            for (int i = 0; i < params.size(); i++) {
                ps.setString(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AdminInfo adminInfo = new AdminInfo();
                adminInfo.setId(rs.getLong("id"));
                adminInfo.setPassword(rs.getString("password"));
                adminInfo.setNickname(rs.getString("nickname"));
                adminInfo.setName(rs.getString("name"));
                adminInfo.setState(rs.getInt("state"));
                adminInfo.setIsDelete(rs.getBoolean("is_deleted"));
                adminInfo.setMobile(rs.getString("mobile"));
                adminInfo.setCreateBy(rs.getLong("create_by"));
                adminInfo.setUpdateBy(rs.getLong("update_by"));
                adminInfo.setCreateTime(ConvertUtils.toInstant(rs.getString("create_time")));
                adminInfo.setUpdateTime(ConvertUtils.toInstant(rs.getString("update_time")));
                list.add(adminInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return list;
    }


    //删除管理员信息
    public void deleteById(Long id) {
        String sql = "delete from admin_info where id = ?";
        try {
            Connection conn = Database.conn();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1,id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    //根据id查询管理员信息
    public AdminInfo queryById(Long id) {
        String sql = "select * from admin_info where id = ?";
        AdminInfo adminInfo = null;
        try {
            Connection conn = Database.conn();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1,id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                adminInfo = new AdminInfo();
                adminInfo.setId(rs.getLong("id"));
                adminInfo.setPassword(rs.getString("password"));
                adminInfo.setNickname(rs.getString("nickname"));
                adminInfo.setName(rs.getString("name"));
                adminInfo.setState(rs.getInt("state"));
                adminInfo.setIsDelete(rs.getBoolean("is_deleted"));
                adminInfo.setMobile(rs.getString("mobile"));
                adminInfo.setCreateBy(rs.getLong("create_by"));
                adminInfo.setUpdateBy(rs.getLong("update_by"));
                adminInfo.setCreateTime(ConvertUtils.toInstant(rs.getString("create_time")));
                adminInfo.setUpdateTime(ConvertUtils.toInstant(rs.getString("update_time")));
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return adminInfo;
    }


    //修改管理员信息
    public void updateById(AdminInfo adminInfo) {
        String sql = "update admin_info set nickname = ?, name = ?, mobile = ?, state = ? where id = ?";
        Connection conn = Database.conn();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,adminInfo.getNickname());
            ps.setString(2,adminInfo.getName());
            ps.setString(3,adminInfo.getMobile());
            ps.setInt(4,adminInfo.getState());
            ps.setLong(5,adminInfo.getId());
            int i = ps.executeUpdate();

           if (i > 0) {
               System.out.println("修改成功");
           }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //新增管理员信息
    public void addAdmin(AdminInfo adminInfo) {
        String sql = "insert into admin_info(nickname, name, mobile, state) values(?,?,?,?)";
        try {
            Connection conn = Database.conn();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,adminInfo.getNickname());
            ps.setString(2,adminInfo.getName());
            ps.setString(3,adminInfo.getMobile());
            ps.setInt(4,adminInfo.getState());
            int i = ps.executeUpdate();
            if (i > 0) {
                System.out.println("添加成功");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




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
    public static void main(String[] args) {
        System.out.println(getCookie(12));
    }



}

