package com.gzy.controller.VO;

public class LoginATM {

    private String nickname;
    private String cardNo;
    private String password;


    public LoginATM() {
    }

    public LoginATM(String nickname, String cardNo, String password) {
        this.nickname = nickname;
        this.cardNo = cardNo;
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
     * @return cardNo
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * 设置
     * @param cardNo
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
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

    public String toString() {
        return "LoginATM{nickname = " + nickname + ", cardNo = " + cardNo + ", password = " + password + "}";
    }
}
