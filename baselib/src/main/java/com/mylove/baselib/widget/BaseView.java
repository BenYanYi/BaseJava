package com.mylove.baselib.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author myLove
 * @time 2017/11/15 10:37
 * @e-mail mylove.520.y@gmail.com
 * @overview
 */

abstract class BaseView extends View {
    private MyThread myThread;

    public BaseView(Context context) {
        super(context);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (myThread == null) {
            myThread = new MyThread();
            myThread.start();
        } else {
            drawSub(canvas);
        }
    }

    /**
     * 绘制处理
     * 在子类中要实现的方法
     *
     * @param canvas
     */
    abstract void drawSub(Canvas canvas);

    abstract void logic();

    private boolean running = true;

    class MyThread extends Thread {
        public void run() {
            super.run();
            while (running) {
                logic();
                postInvalidate();//在线程中更新绘制方法
                try {
                    sleep(30);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void onDetachedFromWindow() {
        running = false;
        super.onDetachedFromWindow();
    }
}
