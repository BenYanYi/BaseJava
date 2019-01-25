package com.mylove.baselib.application;

import android.app.Application;

/**
 * @author BenYanYi
 * @date 2019/01/07 10:18
 * @email ben@yanyi.red
 * @overview
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);
    }
}
