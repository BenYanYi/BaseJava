package com.mylove.baselib.utils;

import android.content.Context;

/**
 * @author myLove
 * @time 2017/11/15 9:51
 * @e-mail mylove.520.y@gmail.com
 * @overview
 */

public class AndroidUtil {
    /**
     * 获取当前版本名称
     */
    private static String getVersionName(Context context) {
        String str = "";
        try {
            str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return str;
    }
}
