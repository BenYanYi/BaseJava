package com.mylove.baselib.utils;

import android.content.Context;

/**
 * @author myLove
 * @time 2017/11/15 9:45
 * @e-mail mylove.520.y@gmail.com
 * @overview
 */

public class DpAndPxUtil {
    /**
     * sp转px
     *
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, Float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * dp转px
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, Float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
