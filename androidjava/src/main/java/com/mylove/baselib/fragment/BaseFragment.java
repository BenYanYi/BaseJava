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

import com.mylove.baselib.utils.StringUtil;
import com.mylove.baselib.utils.toast.ShowToast;
import com.mylove.viewbind.ViewBind;
import com.yanyi.permissionlib.PermissionDialogInfo;
import com.yanyi.permissionlib.PermissionHelper;

/**
 * @author yanyi
 */

public abstract class BaseFragment extends Fragment {
    public View mView;
    public Context mContext;
    public Activity mActivity;

    private PermissionHelper permissionHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(setLayoutID(), null);
        }
        mContext = getActivity();
        mActivity = getActivity();
        ViewBind.bind(mView, this);
        init();
        if (StringUtil.isStringArrayNoEmpty(setPermissions())) {
            permissionHelper = new PermissionHelper(mActivity, setPermissions());
            permissionHelper.setDialogInfo(setDialogInfo());
            permissionHelper.hasPermission(new PermissionHelper.OnPermissionListener() {
                @Override
                public void onAllPermissionSuccess() {
                    allPermissionSuccess();
                }

                @Override
                public void onAllPermissionFailure() {
                    allPermissionFailure();
                }
            });
        }
        if (!isVisibleHidden()) {
            visibleInit();
        }
        return mView;
    }

    public String[] setPermissions() {
        return null;
    }

    /**
     * prompt popup when getting permission
     *
     * @return null
     */
    public PermissionDialogInfo setDialogInfo() {
        return null;
    }

    /**
     * get permissions success
     */
    protected void allPermissionSuccess() {

    }

    /**
     * get permissions failure
     */
    public void allPermissionFailure() {

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        permissionHelper.onActivityResult(requestCode, resultCode, data);
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
}
