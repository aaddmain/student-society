package com.gzy.entiy;

import com.alibaba.fastjson.annotation.JSONField;

import java.time.Instant;
import java.time.ZoneId;


public class AdminInfo {
    private Long id;
    private String password;
    private String nickname;
    private String name;
    private String mobile;
    private Integer state;
    private Boolean isDelete;
    private Long createBy;
    private Long updateBy;
    private Instant createTime;
    private Instant updateTime;


    public AdminInfo() {
    }

    public AdminInfo(Long id, String password, String nickname, String name, String mobile, Integer state, Boolean isDelete, Long createBy, Long updateBy, Instant createTime, Instant updateTime) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.mobile = mobile;
        this.state = state;
        this.isDelete = isDelete;
        this.createBy = createBy;
        this.updateBy = updateBy;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }


    /**
     * 获取
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取
     * @return nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 获取
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     * @return mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置
     * @param mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取
     * @return state
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置
     * @param state
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取
     * @return isDelete
     */
    public Boolean getIsDelete() {
        return isDelete;
    }

    /**
     * 设置
     * @param isDelete
     */
    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 获取
     * @return createBy
     */
    public Long getCreateBy() {
        return createBy;
    }

    /**
     * 设置
     * @param createBy
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取
     * @return updateBy
     */
    public Long getUpdateBy() {
        return updateBy;
    }

    /**
     * 设置
     * @param updateBy
     */
    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 获取
     * @return createTime
     */
    public Instant getCreateTime() {
        return createTime;
    }

    /**
     * 设置
     * @param createTime
     */
    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }



    /**
     * 获取
     * @return updateTime
     */
    public Instant getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置
     * @param updateTime
     */
    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String toString() {
        return "AdminInfo{id = " + id + ", password = " + password + ", nickname = " + nickname + ", name = " + name + ", mobile = " + mobile + ", state = " + state + ", isDelete = " + isDelete + ", createBy = " + createBy + ", updateBy = " + updateBy + ", createTime = " + createTime + ", updateTime = " + updateTime + "}";
    }
}
