package org.ebeijia.tools;

import java.util.Random;

/**
 * Random Number for JAVA（随机数生成类）
 *
 * @version 0.1
 * @Author jinjian.feng
 */
public class RandomNum4J {

    public static void main(String[] args) {
        for (int i = 0; i < 30; i++) {
            Random random = new Random();
            int num = random.nextInt(1);
            System.out.println(num);
        }
    }

    /**
     * 随机生成6位数字验证码
     *
     * @return
     */
    public static String random6() {
        String result = "";
        for (int i = 0; i < 6; i++) {
            Random random = new Random();
            int num = random.nextInt(10);
            result += num;
        }
        return result;
    }

    /**
     * 随机生成8位数字验证码
     *
     * @return
     */
    public static String random8() {
        String result = "";
        for (int i = 0; i < 8; i++) {
            Random random = new Random();
            int num = random.nextInt(10);
            result += num;
        }
        return result;
    }

    //根据长度随机生成字符串
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    //根据长度随机生成纯数字
    public static String getRandomInt(int length) {
        String base = "123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

}
