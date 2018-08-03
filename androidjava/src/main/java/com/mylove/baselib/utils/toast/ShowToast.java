package com.mylove.baselib.utils.toast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

/**
 * @author yanyi
 * 吐司工具类
 */

public class ShowToast {
    @SuppressLint("StaticFieldLeak")
    private static ShowToast instance;
    private Context mContext;
    private String msg;
    private static long lastClickTime;
    private Toast toast;

    private ShowToast(Context mContext) {
        this.mContext = mContext;
    }

    public static ShowToast getInstance(Context context) {
        if (instance == null) {
            synchronized (ShowToast.class) {
                instance = new ShowToast(context);
            }
        }
        return instance;
    }

    @SuppressLint("ShowToast")
    private ShowMsg toast(Object msg) {
        cancel();
        toast = Toast.makeText(mContext, msg.toString(), Toast.LENGTH_SHORT);
        return ShowMsg.getInstance();
    }

    private boolean time() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 2000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public void show(int strID) {
        show(strID, ToastType.DEFAULT);
    }

    public void show(int strID, ToastType type) {
        String msg = mContext.getResources().getString(strID);
        if (!(this.msg != null && this.msg.equals(msg) && time())) {
            toast(msg).show(toast, type);
        }
        this.msg = msg;
    }

    public void show(Object msg) {
        show(msg, ToastType.DEFAULT);
    }

    public void show(Object msg, ToastType type) {
        if (!(this.msg != null && this.msg.equals(msg.toString()) && time())) {
            toast(msg).show(toast, type);
        }
        this.msg = msg.toString();
    }

    public void cancel() {
        if (toast != null) {
            toast.cancel();
        }
    }
}