package com.gzy.dao;

import com.gzy.entiy.UserAmount;
import com.gzy.entiy.UserInfo;
import com.gzy.utils.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserAmountDao {

    /**
     * 获取所有用户的账户信息
     */
    public List<UserAmount> getAllUserAmount(Integer currentPage, Integer pageSize){

        String sql = "select ua.*, ui.nickname as nicknameAmount, ui.name as nameAmount from user_amount as ua left join banksystem.user_info ui on ua.user_id = ui.id where ua.is_deleted = 0 limit ?,?";
        ArrayList<UserAmount> list = new ArrayList<>();
        try {
            Connection conn = Database.conn();
            if (conn == null) {
                throw new RuntimeException("无法获取数据库连接");
            }
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, (currentPage - 1) * pageSize);
            ps.setInt(2, pageSize);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                UserAmount userAmount = new UserAmount();
                userAmount.setId(rs.getLong("id"));
                userAmount.setUserId(rs.getLong("user_id"));
                userAmount.setAmount(rs.getLong("amount"));
                userAmount.setBalance(rs.getLong("balance"));
                userAmount.setState(rs.getInt("state"));
                userAmount.setIsDelete(rs.getBoolean("is_deleted"));
                userAmount.setCreateBy(rs.getLong("create_by"));
                userAmount.setUpdateBy(rs.getLong("update_by"));
                userAmount.setCreateTime(rs.getTimestamp("create_time").toInstant());
                userAmount.setUpdateTime(rs.getTimestamp("update_time").toInstant());
                userAmount.setNicknameAmount(rs.getString("nicknameAmount"));
                userAmount.setNameAmount(rs.getString("nameAmount"));
                list.add(userAmount);
            }
        } catch (SQLException e) {
            System.err.println("查询所有用户账户信息时发生错误: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return list;
    }


    /**
     * 新增账户信息
     */
    public boolean add(Long userId, Integer state) {
        String sql = "insert into user_amount(user_id, state, create_time, update_time) values(?,?,NOW(),NOW())";
        try {
            Connection conn = Database.conn();
            if (conn == null) {
                System.err.println("无法获取数据库连接");
                throw new RuntimeException("无法获取数据库连接");
            }
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, userId);
            ps.setInt(2, state);
            int i = ps.executeUpdate();

            if (i > 0) {
                System.out.println("账户添加成功，用户ID: " + userId);
                return true;
            } else {
                System.out.println("账户添加失败，用户ID: " + userId);
                return false;
            }
        } catch (SQLException e) {
            System.err.println("添加账户信息时发生错误，用户ID: " + userId + ", 错误: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("添加账户信息失败: " + e.getMessage(), e);
        }
    }

    /**
     * 删除账户信息
     */
    public void deleteById(Long userId) {
        String sql = "update user_amount set is_deleted = 1, update_time = NOW() where user_id = ?";
        try {
            Connection conn = Database.conn();
            if (conn == null) {
                throw new RuntimeException("无法获取数据库连接");
            }
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, userId);
            int i = ps.executeUpdate();
            if (i > 0) {
                System.out.println("删除成功");
            }
        } catch (SQLException e) {
            System.err.println("删除账户信息时发生错误: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改账户信息
     */
    public void updateByUserId(Long userId, Integer state) {
        String sql = "update user_amount set state = ?, update_time = NOW() where user_id = ?";
        try {
            Connection conn = Database.conn();
            if (conn == null) {
                throw new RuntimeException("无法获取数据库连接");
            }
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, state);
            ps.setLong(2, userId);
            int i = ps.executeUpdate();

            if (i > 0) {
                System.out.println("修改成功");
            } else {
                System.out.println("修改失败");
            }
        } catch (SQLException e) {
            System.err.println("修改账户信息时发生错误: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据用户ID查询账户信息
     * @param userId 用户ID
     * @return 用户账户信息
     */
    public UserAmount queryByUserId(Long userId) {
        String sql = "select * from user_amount where user_id = ? and is_deleted = 0";
        try {
            Connection conn = Database.conn();
            if (conn == null) {
                throw new RuntimeException("无法获取数据库连接");
            }
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                UserAmount userAmount = new UserAmount();
                userAmount.setId(rs.getLong("id"));
                userAmount.setUserId(rs.getLong("user_id"));
                // 修复：检查列是否存在，如果不存在则设置为0
                try {
                    userAmount.setAmount(rs.getLong("amount"));
                } catch (Exception e) {
                    userAmount.setAmount(0L); // 默认值
                }
                userAmount.setBalance(rs.getLong("balance"));
                userAmount.setState(rs.getInt("state"));
                userAmount.setIsDelete(rs.getBoolean("is_deleted"));
                userAmount.setCreateBy(rs.getLong("create_by"));
                userAmount.setUpdateBy(rs.getLong("update_by"));
                userAmount.setCreateTime(rs.getTimestamp("create_time").toInstant());
                userAmount.setUpdateTime(rs.getTimestamp("update_time").toInstant());
                return userAmount;
            }
        } catch (SQLException e) {
            System.err.println("根据用户ID查询账户信息时发生错误: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * 条件查询(用户名，姓名)
     */
    public List<UserAmount> queryByConditions(String nicknameAmount, String nameAmount, Integer currentPage, Integer pageSize) {
        String sql = "select ua.*, ui.nickname as nicknameAmount, ui.name as nameAmount from user_amount as ua left join user_info ui on ua.user_id = ui.id where ua.is_deleted = 0";
        ArrayList<UserAmount> list = new ArrayList<>();
        if (nicknameAmount != null && !nicknameAmount.equals("")) {
            sql += " and ui.nickname like '%" + nicknameAmount + "%'";
        }

        if (nameAmount != null && !nameAmount.equals("")) {
            sql += " and ui.name like '%" + nameAmount + "%'";
        }

        sql += " limit ?,?";

        try {
            Connection conn = Database.conn();
            if (conn == null) {
                throw new RuntimeException("无法获取数据库连接");
            }
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, (currentPage - 1) * pageSize);
            ps.setInt(2, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserAmount userAmount = new UserAmount();
                userAmount.setId(rs.getLong("id"));
                userAmount.setUserId(rs.getLong("user_id"));
                userAmount.setAmount(rs.getLong("amount"));
                userAmount.setBalance(rs.getLong("balance"));
                userAmount.setState(rs.getInt("state"));
                userAmount.setIsDelete(rs.getBoolean("is_deleted"));
                userAmount.setCreateBy(rs.getLong("create_by"));
                userAmount.setUpdateBy(rs.getLong("update_by"));
                userAmount.setCreateTime(rs.getTimestamp("create_time").toInstant());
                userAmount.setUpdateTime(rs.getTimestamp("update_time").toInstant());
                userAmount.setNicknameAmount(rs.getString("nicknameAmount"));
                userAmount.setNameAmount(rs.getString("nameAmount"));
                list.add(userAmount);
            }
        } catch (SQLException e) {
            System.err.println("条件查询账户信息时发生错误: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return list;
    }


    /**
     * 查询总记录数
     * @return
     */
    public int getTotalCount() {
        String sql = "select count(*) from user_amount where is_deleted = 0";
        try {
            Connection conn = Database.conn();
            if (conn == null) {
                throw new RuntimeException("无法获取数据库连接");
            }
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("查询总记录数时发生错误: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return 0;
    }

}
