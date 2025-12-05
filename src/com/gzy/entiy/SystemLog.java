package com.gzy.entiy;
import java.time.Instant;
public class SystemLog {
    private Long id;
    private Integer type; // 类型，0登出，1登入
    private Integer userType; // 用户类型，0管理员，1用户
    private Long userId; // 用户id
    private Integer state; // 状态, 0禁用，1正常
    private Boolean deleted; // 是否删除，1是，0否
    private Long createBy; // 创建者id, 1 - 管理员
    private Long updateBy; // 更新者id, 1 - 管理员
    private Instant createTime; // 创建时间
    private Instant updateTime; // 更新时间

    public SystemLog() {
    }

    public SystemLog(Long id, Integer type, Integer userType, Long userId, Integer state, Boolean deleted, Long createBy, Long updateBy, Instant createTime, Instant updateTime) {
        this.id = id;
        this.type = type;
        this.userType = userType;
        this.userId = userId;
        this.state = state;
        this.deleted = deleted;
        this.createBy = createBy;
        this.updateBy = updateBy;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "SystemLog{" +
                "id=" + id +
                ", type=" + type +
                ", userType=" + userType +
                ", userId=" + userId +
                ", state=" + state +
                ", deleted=" + deleted +
                ", createBy=" + createBy +
                ", updateBy=" + updateBy +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}

