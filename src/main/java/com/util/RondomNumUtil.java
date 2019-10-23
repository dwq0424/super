package com.util;

import javax.servlet.http.HttpSession;

public class RondomNumUtil {

    /**
     * 随机生成6位随机验证码
     */
    public static String createRandomVcode() {
        //验证码
        String vcode = "";
        for (int i = 0; i < 6; i++) {
            vcode = vcode + (int) (Math.random() * 9);
        }
        return vcode;
    }

}
