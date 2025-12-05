package com.gzy.entiy;

import java.time.Instant;

public class UserAmount {
    private Long id;
    private Long userId;
    private Long amount;
    private Long balance;
    private Integer state;
    private Boolean isDelete;
    private Long createBy;
    private Long updateBy;
    private Instant createTime;
    private Instant updateTime;

    //用户信息昵称/姓名
    private String nicknameAmount;
    private String nameAmount;


    public UserAmount() {
        // Initialize default values
        this.amount = 0L;
        this.balance = 0L;
        this.state = 1;
        this.isDelete = false;
        this.createBy = 1L;
        this.updateBy = 1L;
    }

    public UserAmount(Long id, Long userId, Long amount, Long balance, Integer state, Boolean isDelete, Long createBy, Long updateBy, Instant createTime, Instant updateTime, String nicknameAmount, String nameAmount) {
        this.id = id;
        this.userId = userId;
        this.amount = amount != null ? amount : 0L;
        this.balance = balance != null ? balance : 0L;
        this.state = state != null ? state : 1;
        this.isDelete = isDelete != null ? isDelete : false;
        this.createBy = createBy != null ? createBy : 1L;
        this.updateBy = updateBy != null ? updateBy : 1L;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.nicknameAmount = nicknameAmount;
        this.nameAmount = nameAmount;
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
     * @return userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取
     * @return amount
     */
    public Long getAmount() {
        return amount;
    }

    /**
     * 设置
     * @param amount
     */
    public void setAmount(Long amount) {
        this.amount = amount;
    }

    /**
     * 获取
     * @return balance
     */
    public Long getBalance() {
        return balance;
    }

    /**
     * 设置
     * @param balance
     */
    public void setBalance(Long balance) {
        this.balance = balance;
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

    /**
     * 获取
     * @return nicknameAmount
     */
    public String getNicknameAmount() {
        return nicknameAmount;
    }

    /**
     * 设置
     * @param nicknameAmount
     */
    public void setNicknameAmount(String nicknameAmount) {
        this.nicknameAmount = nicknameAmount;
    }

    /**
     * 获取
     * @return nameAmount
     */
    public String getNameAmount() {
        return nameAmount;
    }

    /**
     * 设置
     * @param nameAmount
     */
    public void setNameAmount(String nameAmount) {
        this.nameAmount = nameAmount;
    }

    public String toString() {
        return "UserAmount{id = " + id + ", userId = " + userId + ", amount = " + amount + ", balance = " + balance + ", state = " + state + ", isDelete = " + isDelete + ", createBy = " + createBy + ", updateBy = " + updateBy + ", createTime = " + createTime + ", updateTime = " + updateTime + ", nicknameAmount = " + nicknameAmount + ", nameAmount = " + nameAmount + "}";
    }
}
