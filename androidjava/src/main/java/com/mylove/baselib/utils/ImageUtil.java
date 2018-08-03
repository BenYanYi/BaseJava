package com.mylove.baselib.utils;

import android.graphics.Bitmap;

/**
 * @author yanyi
 */

public class ImageUtil {
    /**
     * 计较两张图片是否一样
     *
     */
    public boolean isEquals(Bitmap b1, Bitmap b2) {
        //先判断宽高是否一致，不一致直接返回false
        if (b1.getWidth() == b2.getWidth()
                && b1.getHeight() == b2.getHeight()) {
            int xCount = b1.getWidth();
            int yCount = b1.getHeight();
            for (int x = 0; x < xCount; x++) {
                for (int y = 0; y < yCount; y++) {
                    //比较每个像素点颜色
                    if (b1.getPixel(x, y) != b2.getPixel(x, y)) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
