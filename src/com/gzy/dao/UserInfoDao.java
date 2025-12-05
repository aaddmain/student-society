package com.gzy.dao;

import com.gzy.entiy.AdminInfo;
import com.gzy.entiy.UserInfo;
import com.gzy.utils.ConvertUtils;
import com.gzy.utils.Database;
import com.gzy.utils.PageResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserInfoDao {

    /**
     * 查询全部用户信息
     * @return
     */
    public List<UserInfo> queryByAll(Integer currentPage, Integer pageSize) {
        String sql = "select * from user_info where is_deleted = 0 limit ?,?";
        ArrayList<UserInfo> list = new ArrayList<>();
        try {
            Connection conn = Database.conn();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, (currentPage - 1) * pageSize);
            ps.setInt(2, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserInfo userInfo = new UserInfo();
                userInfo.setId(rs.getLong("id"));
                userInfo.setCardType(rs.getString("card_type"));
                userInfo.setCardNo(rs.getString("card_no"));
                userInfo.setPassword(rs.getString("password"));
                userInfo.setNickname(rs.getString("nickname"));
                userInfo.setName(rs.getString("name"));
                userInfo.setAddress(rs.getString("address"));
                userInfo.setIdNum(rs.getString("id_num"));
                userInfo.setMobile(rs.getString("mobile"));
                userInfo.setState(rs.getInt("state"));
                userInfo.setDeleted(rs.getBoolean("is_deleted"));
                userInfo.setCreateBy(rs.getLong("create_by"));
                userInfo.setUpdateBy(rs.getLong("update_by"));
                userInfo.setCreateTime(rs.getTimestamp("create_time").toInstant());
                userInfo.setUpdateTime(rs.getTimestamp("update_time").toInstant());
                list.add(userInfo);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    /**
     * 根据条件查询用户信息
     * @param cardType
     * @param cardNo
     * @param nickname
     * @param name
     * @param address
     * @param idNum
     * @param mobile
     * @return
     */
    //根据条件查询
    public List<UserInfo> queryByConditions(String nickname, String name, String mobile, Integer cardType, String cardNo, String address, String idNum, Integer currentPage, Integer pageSize) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select * from user_info where is_deleted = 0");

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

        if (cardType != null) {
            sqlBuilder.append(" and card_type = ?");
            params.add(cardType.toString());
        }

        if (cardNo != null && !cardNo.trim().isEmpty()) {
            sqlBuilder.append(" and card_no = ?");
            params.add(cardNo);
        }

        if (address != null && !address.trim().isEmpty()) {
            sqlBuilder.append(" and address like ?");
            params.add("%" + address + "%");
        }

        if (idNum != null && !idNum.trim().isEmpty()) {
            sqlBuilder.append(" and id_num = ?");
            params.add(idNum);
        }

        //添加分页参数
        sqlBuilder.append(" limit ?,?");
        int offset = (currentPage - 1) * pageSize;

        String sql = sqlBuilder.toString();

        ArrayList<UserInfo> list = new ArrayList<>();

        try {
            Connection conn = Database.conn();
            PreparedStatement ps = conn.prepareStatement(sql);

            //设置参数
            for (int i = 0; i < params.size(); i++) {
                ps.setString(i + 1, params.get(i));
            }

            //设置limit参数为整数
            ps.setInt(params.size() + 1, offset);
            ps.setInt(params.size() + 2, pageSize);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserInfo userInfo = new UserInfo();
                userInfo.setId(rs.getLong("id"));
                userInfo.setCardType(rs.getString("card_type"));
                userInfo.setCardNo(rs.getString("card_no"));
                userInfo.setPassword(rs.getString("password"));
                userInfo.setNickname(rs.getString("nickname"));
                userInfo.setName(rs.getString("name"));
                userInfo.setAddress(rs.getString("address"));
                userInfo.setIdNum(rs.getString("id_num"));
                userInfo.setMobile(rs.getString("mobile"));
                userInfo.setState(rs.getInt("state"));
                userInfo.setDeleted(rs.getBoolean("is_deleted"));
                userInfo.setCreateBy(rs.getLong("create_by"));
                userInfo.setUpdateBy(rs.getLong("update_by"));
                userInfo.setCreateTime(ConvertUtils.toInstant(rs.getString("create_time")));
                userInfo.setUpdateTime(ConvertUtils.toInstant(rs.getString("update_time")));
                list.add(userInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return list;
    }


    /**
     * 根据id删除用户信息
     * @param id
     * @return
     */
    public void deleteById(Long id) {
        String sql = "delete from user_info where id = ?";
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

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    public UserInfo queryById(Long id) {
        String sql = "select * from user_info where id = ?";
        Connection conn = Database.conn();
        UserInfo userInfo = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1,id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                userInfo = new UserInfo();
                userInfo.setId(rs.getLong("id"));
                userInfo.setCardType(rs.getString("card_type"));
                userInfo.setCardNo(rs.getString("card_no"));
                userInfo.setPassword(rs.getString("password"));
                userInfo.setNickname(rs.getString("nickname"));
                userInfo.setName(rs.getString("name"));
                userInfo.setAddress(rs.getString("address"));
                userInfo.setIdNum(rs.getString("id_num"));
                userInfo.setMobile(rs.getString("mobile"));
                userInfo.setState(rs.getInt("state"));
                userInfo.setDeleted(rs.getBoolean("is_deleted"));
                userInfo.setCreateBy(rs.getLong("create_by"));
                userInfo.setUpdateBy(rs.getLong("update_by"));
                userInfo.setCreateTime(ConvertUtils.toInstant(rs.getString("create_time")));
                userInfo.setUpdateTime(ConvertUtils.toInstant(rs.getString("update_time")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userInfo;
    }


    /**
     * 添加用户信息
     * @return
     */
    public Long add(UserInfo userInfo) {
        String sql = "insert into user_info(card_type, card_no, nickname, name, address, id_num, mobile, state, password) values(?,?,?,?,?,?,?,?,?)";
        try {
            Connection conn = Database.conn();
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1,userInfo.getCardType());
            ps.setString(2,userInfo.getCardNo());
            ps.setString(3,userInfo.getNickname());
            ps.setString(4,userInfo.getName());
            ps.setString(5,userInfo.getAddress());
            ps.setString(6,userInfo.getIdNum());
            ps.setString(7,userInfo.getMobile());
            ps.setInt(8,userInfo.getState());
            ps.setString(9,userInfo.getPassword());
            int i = ps.executeUpdate();

            if (i > 0) {
                //获取的 id
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    Long userId = (generatedKeys.getLong(1));
                    return userId;
                }
                System.out.println("添加成功");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * 修改用户信息
     * @param userInfo
     * @return
     */
    public void updateById(UserInfo userInfo) {
        String sql = "update user_info set card_type = ?, card_no = ?, nickname = ?, name = ?, address = ?, id_num = ?, mobile = ?, state = ? where id = ?";
        try {
            Connection conn = Database.conn();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,userInfo.getCardType());
            ps.setString(2,userInfo.getCardNo());
            ps.setString(3,userInfo.getNickname());
            ps.setString(4,userInfo.getName());
            ps.setString(5,userInfo.getAddress());
            ps.setString(6,userInfo.getIdNum());
            ps.setString(7,userInfo.getMobile());
            ps.setInt(8,userInfo.getState());
            ps.setLong(9,userInfo.getId());

            int i = ps.executeUpdate();
            if (i > 0) {
                System.out.println("修改成功");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询总记录数
     * @return
     */
    public int getTotalCount() {
        String sql = "select count(*) from user_info where is_deleted = 0";
        try {
            Connection conn = Database.conn();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    /**
     * 根据昵称和卡号查询用户信息（精确匹配）
     * @param nickname 用户昵称
     * @param cardNo 卡号
     * @return 用户信息
     */
    public UserInfo queryByNicknameAndCardNo(String nickname, String cardNo) {
        String sql = "select * from user_info where nickname = ? and card_no = ? and is_deleted = 0";
        Connection conn = Database.conn();
        UserInfo userInfo = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nickname);
            ps.setString(2, cardNo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                userInfo = new UserInfo();
                userInfo.setId(rs.getLong("id"));
                userInfo.setCardType(rs.getString("card_type"));
                userInfo.setCardNo(rs.getString("card_no"));
                userInfo.setPassword(rs.getString("password"));
                userInfo.setNickname(rs.getString("nickname"));
                userInfo.setName(rs.getString("name"));
                userInfo.setAddress(rs.getString("address"));
                userInfo.setIdNum(rs.getString("id_num"));
                userInfo.setMobile(rs.getString("mobile"));
                userInfo.setState(rs.getInt("state"));
                userInfo.setDeleted(rs.getBoolean("is_deleted"));
                userInfo.setCreateBy(rs.getLong("create_by"));
                userInfo.setUpdateBy(rs.getLong("update_by"));
                
                // Handle potential null values for time fields
                String createTimeStr = rs.getString("create_time");
                String updateTimeStr = rs.getString("update_time");
                
                if (createTimeStr != null && !createTimeStr.isEmpty()) {
                    userInfo.setCreateTime(ConvertUtils.toInstant(createTimeStr));
                }
                
                if (updateTimeStr != null && !updateTimeStr.isEmpty()) {
                    userInfo.setUpdateTime(ConvertUtils.toInstant(updateTimeStr));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception for debugging
            throw new RuntimeException(e);
        }

        return userInfo;
    }
    
    /**
     * 检查是否存在相同的昵称
     * @param nickname 昵称
     * @return 是否存在相同昵称的用户
     */
    public boolean existsByNickname(String nickname) {
        String sql = "select count(*) from user_info where nickname = ? and is_deleted = 0";
        try {
            Connection conn = Database.conn();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nickname);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    
    /**
     * 检查是否存在相同的卡号
     * @param cardNo 卡号
     * @return 是否存在相同卡号的用户
     */
    public boolean existsByCardNo(String cardNo) {
        String sql = "select count(*) from user_info where card_no = ? and is_deleted = 0";
        try {
            Connection conn = Database.conn();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cardNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    
    /**
     * 检查是否存在相同的身份证号
     * @param idNum 身份证号
     * @return 是否存在相同身份证号的用户
     */
    public boolean existsByIdNum(String idNum) {
        String sql = "select count(*) from user_info where id_num = ? and is_deleted = 0";
        try {
            Connection conn = Database.conn();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idNum);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

}
