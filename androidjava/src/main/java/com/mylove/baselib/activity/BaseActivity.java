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

import com.google.gson.Gson;
import com.mylove.baselib.R;
import com.mylove.baselib.grobal.AppManager;
import com.mylove.baselib.utils.toast.ShowToast;
import com.mylove.viewbind.ViewBind;

/**
 * @author yanyi
 */

public abstract class BaseActivity extends AppCompatActivity {
    public Context mContext;
    public Activity mActivity;
    public Gson gson = new Gson();
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
            if (canBack()) {
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setDisplayHomeAsUpEnabled(true);
                }
            }
            getSupportActionBar().setTitle("");
        }
        ViewBind.bind(this);
        init(savedInstanceState);
    }

    /**
     * set layoutID
     *
     * @return layoutID
     */
    protected abstract int setLayoutID();

    /**
     * Logic code
     */
    protected abstract void init(Bundle savedInstanceState);

    /**
     * Whether to return
     *
     * @return default false
     */
    public boolean canBack() {
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
     * Close the soft keyboard
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
     * start Activity
     *
     * @param cls page that needs to jump
     */
    public void startAct(Class cls) {
        Intent intent = new Intent(mContext, cls);
        startActivity(intent);
    }

    /**
     * start Activity
     *
     * @param cls    page that needs to jump
     * @param bundle bundle
     */
    public void startAct(Class cls, Bundle bundle) {
        Intent intent = new Intent(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * start Activity
     *
     * @param cls         page that needs to jump
     * @param requestCode requestCode
     */
    public void startAct(Class cls, int requestCode) {
        Intent intent = new Intent(mContext, cls);
        startActivityForResult(intent, requestCode);
    }

    /**
     * start Activity
     *
     * @param cls         page that needs to jump
     * @param bundle      bundle
     * @param requestCode requestCode
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
     * Whether to quit the software
     *
     * @return default false
     */
    public boolean isExit() {
        return false;
    }

    /**
     * Whether to quit the software
     */
    private boolean isExit;

    /**
     * rewrite onKeyDown
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
     * exit method
     */
    private void exit() {
        if (!isExit) {
            isExit = true;
            toast("再按一次退出程序");
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            appExit();
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

    public void appExit() {

    }
}
