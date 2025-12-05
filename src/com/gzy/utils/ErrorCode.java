package com.gzy.utils;
public enum ErrorCode {
    USER_NOT_EXIST("USER_NOT_EXIST", "用户不存在"),
    PWD_ERR("PWD_ERR", "密码错误"),
    ADMIN_NOT_EXIST("ADMIN_NOT_EXIST", "管理员不存在"),
    LOGIN_TYPE_ERROR("LOGIN_TYPE_ERROR", "错误的登录类型"),
    NOT_LOGIN("NOT_LOGIN", "当前未登录"),
    REPEAT_PWD_NOT_SAME("REPEAT_PWD_NOT_SAME", "两次密码不一致"),
    ADMIN_NOT_LOGIN("ADMIN_NOT_LOGIN", "管理员未登录"),
    ADMIN_MOBILE_SAME("ADMIN_MOBILE_SAME", "相同手机号的管理员已存在"),
    USER_MOBILE_SAME("USER_MOBILE_SAME", "相同手机号的用户已存在"),
    USER_CARDNO_OR_IDNUM_SAME("USER_CARDNO_OR_IDNUM_SAME", "相同卡号或身份证号的用户已存在"),
    OPERATION_IS_NULL("OPERATION_IS_NULL", "选择操作operation: freeze冻结/unfreeze解冻/delete删除"),
    USER_AMOUNT_NOT_EXIST("USER_AMOUNT_NOT_EXIST", "用户账户信息不存在"),
    TRANSACTION_OUT_HAS_NO_CARNO("TRANSACTION_OUT_HAS_NO_CARNO", "转账操作-对方卡号不可为空"),
    TRANSACTION_OUT_SUCH_USER("TRANSACTION_OUT_SUCH_USER", "转账操作-对方账户或余额信息不存在"),
    USER_AMOUNT_INSUFFICIENT("USER_AMOUNT_INSUFFICIENT", "账户余额不足"),
    USER_AMOUNT_UNDER_ZERO("USER_AMOUNT_UNDER_ZERO", "金额不可小于0"),
    ID_FIELD_BLANK("ID_ FIELD_BLANK", "id不能为空"),
    PARAM_FORMAT_ERROR("PARAM_FORMAT_ERROR", "参数格式错误"),
    NICKNAME_FIELD_BLANK("NICKNAME_FIELD_BLANK", "昵称不能为空"),
    NAME_FIELD_BLANK("NAME_FIELD_BLANK", "姓名不能为空"),
    MOBILE_FIELD_BLANK("MOBILE_FIELD_BLANK", "手机号不能为空"),
    CARD_TYPE_FIELD_BLANK("CARD_TYPE_FIELD_BLANK", "证件类型不能为空"),
    CARD_NO_FIELD_BLANK("CARD_NO_FIELD_BLANK", "证件号码不能为空"),
    ADDRESS_FIELD_BLANK("ADDRESS_FIELD_BLANK",  "地址不能为空"),
    ID_NUM_FIELD_BLANK("ID_NUM_FIELD_BLANK", "身份证号不能为空"),
    SYSTEM_ERROR("SYSTEM_ERROR", "系统错误"),
    UNKNOWN_ERROR("UNKNOWN_ERROR", "未知错误"),
    PWD_FIELD_BLANK("PWD_FIELD_BLANK", "密码不能为空"),
    USER_ACCOUNT_DISABLED("USER_ACCOUNT_DISABLED", "用户账户已被禁用"),
    USER_NOT_LOGIN("USER_NOT_LOGIN", "用户未登录"),
    USER_PWD_ERROR("USER_PWD_ERROR", "用户密码错误"),
    INSUFFICIENT_BALANCE("INSUFFICIENT_BALANCE", "账户余额不足");
    private String code;
    private String message;
    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 操作类型, 0取款，1存款，2转出，3转入
     */
    public static final Integer TRANSACTION_TYPE_OUT = 0;
    public static final Integer TRANSACTION_TYPE_IN = 1;
    public static final Integer TRANSACTION_TYPE_TRANSACTION_OUT = 2;
    public static final Integer TRANSACTION_TYPE_TRANSACTION_IN = 3;
}