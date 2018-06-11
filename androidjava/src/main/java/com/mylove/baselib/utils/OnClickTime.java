package com.mylove.baselib.utils;

/**
 * @author myLove
 * 用于判断是否点击两次
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
