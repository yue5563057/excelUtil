package com.dongyue.util;

import com.dongyue.util.exception.MyException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

public class StringUtil {

    private StringUtil() {

    }

    /**
     * 判断字符串是否为空或空值
     * @param str 需要检测的字符串
     * @return true为空 false不为空
     */
    public static boolean isNull(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 判断字符串是否为空或空值
     *
     * @param str  需要检测的字符串
     * @return true非空 false为空
     */
    public static boolean isNotNull(String str) {
        return str != null && str.length() > 0;
    }

    /**
     * 生成UUID(去掉中间横杠)
     * @return 去除横杆的UUID字符串
     */
    public static String generId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成6位数字字符串
     * @return 6位数字字符串
     */
    public static String get6Code() {
        Random random = null;
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            result.append(random.nextInt(10));
        }
        return result.toString();
    }

    private static String encode = "xfarmer";


    public static String stringToMd5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new MyException("没有这个md5算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);

        if (md5code.length() != 32) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 32 - md5code.length(); i++) {
                sb.append("0").append(md5code);
            }
            return sb.toString();
        } else {
            return md5code;
        }

    }


}
