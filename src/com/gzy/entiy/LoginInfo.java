package com.gzy.entiy;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
public class LoginInfo {
    public static Map<String,AdminInfo> ADMIN_LOGIN_MAP = new HashMap<>();
    public static Map<String,UserInfo> USER_LOGIN_MAP = new HashMap<>();
    public static String getRandomSting(int length) {
        String base ="abcdefghijkmnopqrstuwxyz0123456789";
        Random random   = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length;i++){
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

}
