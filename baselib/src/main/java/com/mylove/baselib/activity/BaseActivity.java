package com.mylove.baselib.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mylove.baselib.R;
import com.mylove.baselib.grobal.AppManager;
import com.mylove.baselib.utils.toast.ShowToast;

import butterknife.ButterKnife;

/**
 * @author myLove
 * @time 2018/1/8 13:55
 * @e-mail love@yanyi.red
 * @overview
 */

public abstract class BaseActivity extends AppCompatActivity {
    public Context mContext;
    public Activity mActivity;
    public Gson gson = new Gson();
    public TextView rightTv;
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        AppManager.getInstance().addActivity(this);
        setContentView(setLayoutID());
        mContext = this;
        mActivity = this;
        mToolbar = findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            //设置是否有返回箭头
            if (callback()) {
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    //设置ActionBar一个返回箭头，主界面没有，次级界面有
                    actionBar.setDisplayHomeAsUpEnabled(true);
                }
            }
            getSupportActionBar().setTitle("");
        }
        ButterKnife.bind(this);
        init();
    }

    /**
     * 设置页面
     *
     * @return 页面
     */
    protected abstract int setLayoutID();

    /**
     * 逻辑代码
     */
    protected abstract void init();

    /**
     * 是否需要返回键
     *
     * @return false
     */
    public boolean callback() {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 关闭软键盘
     */
    public void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 页面跳转
     *
     * @param cls 需要跳转的页面
     */
    public void startAct(Class cls) {
        Intent intent = new Intent(mContext, cls);
        startActivity(intent);
    }

    /**
     * 页面跳转
     *
     * @param cls    需要跳转的页面
     * @param bundle 传递的参数
     */
    public void startAct(Class cls, Bundle bundle) {
        Intent intent = new Intent(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 带返回的跳转页面
     *
     * @param cls         需要跳转的页面
     * @param requestCode 请求的下标
     */
    public void startAct(Class cls, int requestCode) {
        Intent intent = new Intent(mContext, cls);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 带返回的跳转页面
     *
     * @param cls         需要跳转的页面
     * @param bundle      传递的参数
     * @param requestCode 请求的下标
     */
    public void startAct(Class cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public void toast(Object obj) {
        ShowToast.getInstance(mContext).show(obj);
    }

    public void toastTest() {
        ShowToast.getInstance(mContext).show("功能有待开发");
    }

    /**
     * 是否双击退出应用
     *
     * @return 默认false
     */
    public boolean isExit() {
        return false;
    }

    /**
     * 标记是否退出
     */
    private boolean isExit;

    /**
     * 重写onKeyDown方法
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && isExit()) {
            exit();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    /**
     * 退出方法
     */
    private void exit() {
        if (!isExit) {
            isExit = true;
            toast("再按一次退出程序");
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            AppManager.getInstance().AppExit(mContext);
        }
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
}
