package com.mylove.baselib.utils.toast;

import android.view.Gravity;
import android.widget.Toast;

/**
 * @author yanyi
 * 吐司显示位置类
 */

class ShowMsg {

    private static ShowMsg instance;

    static ShowMsg getInstance() {
        if (instance == null) {
            synchronized (ShowMsg.class) {
                if (instance == null) {
                    instance = new ShowMsg();
                }
            }
        }
        return instance;
    }

    void show(Toast toast, ToastType type) {
        if (null != type) {
            switch (type) {
                case TOP:
                    toast.setGravity(Gravity.TOP, 0, 0);
                    break;
                case BOTTOM:
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    break;
                case LEFT:
                    toast.setGravity(Gravity.LEFT, 0, 0);
                    break;
                case RIGHT:
                    toast.setGravity(Gravity.RIGHT, 0, 0);
                    break;
                case CENTER:
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    break;
                case CENTER_HORIZONTAL:
                    toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    break;
                case CENTER_VERTICAL:
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    break;
                case FILL_HORIZONTAL:
                    toast.setGravity(Gravity.FILL_HORIZONTAL, 0, 0);
                    break;
                case FILL_VERTICAL:
                    toast.setGravity(Gravity.FILL_VERTICAL, 0, 0);
                    break;
                case DEFAULT:
                    break;
                default:
                    break;
            }
        }
        toast.show();
    }
}
