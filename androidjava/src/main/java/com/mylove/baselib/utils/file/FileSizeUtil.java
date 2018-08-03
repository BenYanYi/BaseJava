package com.mylove.baselib.utils.file;

import com.mylove.loglib.JLog;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

/**
 * @author yanyi
 */

public class FileSizeUtil {

    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */
    public static double getFileOrFilesSize(String filePath, SizeType sizeType) {
        File file = new File(filePath);
        long blockSize;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JLog.e("获取文件大小失败!");
            return 0;
        }
        return FormatFileSize(blockSize, sizeType);
    }

    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @param filePath 文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static String getAutoFileOrFilesSize(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JLog.e("获取文件大小失败!");
            return "获取文件大小失败";
        }
        return FormatFileSize(blockSize);
    }

    /**
     * 获取指定文件大小
     */
    public static long getFileSize(File file) {
        long size = 0;
        try {
            if (file.exists()) {
                FileInputStream fis = null;
                fis = new FileInputStream(file);
                size = fis.available();
            } else {
                file.createNewFile();
                JLog.e("获取文件大小失败!");
                return 0;
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            size = 0;
        }
        return size;
    }

    /**
     * 获取指定文件夹
     */
    public static long getFileSizes(File f) {
        long size = 0;
        try {
            File files[] = f.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    size = size + getFileSizes(files[i]);
                } else {
                    size = size + getFileSize(files[i]);
                }
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            return 0;
        }
        return size;
    }

    /**
     * 转换文件大小
     */
    private static String FormatFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString;
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 转换文件大小,指定转换的类型
     */
    private static double FormatFileSize(long fileS, SizeType sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case TYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case TYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case TYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case TYPE_GB:
                fileSizeLong = Double.valueOf(df
                        .format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }
}
