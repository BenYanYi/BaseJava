package com.mylove.baselib.utils;

import android.content.Context;

/**
 * @author myLove
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
