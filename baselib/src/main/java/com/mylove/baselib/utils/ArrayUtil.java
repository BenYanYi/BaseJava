package com.mylove.baselib.utils;

import java.util.Arrays;

/**
 * @author myLove
 * @time 2017/11/15 9:37
 * @e-mail mylove.520.y@gmail.com
 * @overview
 */

public class ArrayUtil {

    /**
     * 将多个数组合并成一个数组
     *
     * @param first
     * @param rest
     * @param <T>
     * @return
     */
    public static <T> T[] concatAll(T[] first, T[]... rest) {
        int totalLength = first.length;
        for (T[] t : rest) {
            totalLength += t.length;
        }
        T[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (T[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }
}
