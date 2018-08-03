package com.mylove.baselib.utils;

import java.util.Arrays;

/**
 * @author yanyi
 */

public class ArrayUtil {

    /**
     * 将多个数组合并成一个数组
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
