package com.mylove.baselib.utils;

/**
 * @author myLove
 * @time 2017/11/15 9:54
 * @e-mail mylove.520.y@gmail.com
 * @overview 用于判断是否点击两次
 */

public class OnClickTime {
    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 600) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
