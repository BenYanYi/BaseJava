package com.mylove.baselib.utils;

import android.content.Context;

/**
 * @author myLove
 */

public class DpAndPxUtil {
    /**
     * sp转px
     *
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
