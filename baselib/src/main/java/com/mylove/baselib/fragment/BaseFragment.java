package com.mylove.baselib.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.mylove.baselib.utils.toast.ShowToast;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author myLove
 * @time 2017/11/1 14:16
 * @e-mail mylove.520.y@gmail.com
 * @overview
 */

public abstract class BaseFragment extends Fragment {
    public View mView;
    public Context mContext;
    public Activity mActivity;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(setLayoutID(), null);
        }
        mContext = getActivity();
        mActivity = getActivity();
        unbinder = ButterKnife.bind(this, mView);
        init();
        if (!isVisibleHidden()) {
            visibleInit();
        }
        return mView;
    }

    protected abstract int setLayoutID();

    protected abstract void init();

    /**
     * 显示当前界面时页面操作
     */
    public void visibleInit() {
    }

    public boolean isVisibleHidden() {
        return false;
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

    /**
     * 关闭软键盘
     */
    public void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && mActivity.getCurrentFocus() != null) {
            if (mActivity.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public void toast(Object obj) {
        ShowToast.getInstance(mContext).show(obj);
    }

    public void toastTest() {
        ShowToast.getInstance(mContext).show("功能有待开发");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleHidden()) {
            if (isVisibleToUser && isResumed()) {
                onResume();
            } else {
                onPause();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint() && isVisibleHidden()) {
            visibleInit();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
