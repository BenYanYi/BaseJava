package com.mylove.baselib.activity.permission;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.mylove.baselib.activity.BaseActivity;
import com.mylove.loglib.JLog;

import didikee.com.permissionshelper.PermissionsHelper;
import didikee.com.permissionshelper.info.DialogInfo;

/**
 * @author myLove
 * @time 2017/11/24 16:33
 * @e-mail mylove.520.y@gmail.com
 * @overview 获取权限activity
 */

public abstract class BasePermissionsActivity extends BaseActivity {
    private PermissionsHelper permissionsHelper;

//    protected abstract PermissionType[] setPermissions();

    protected abstract String[] setPermissions();

    @Override
    protected void init() {
//        permissionsHelper = new PermissionsHelper(mActivity, permissions(), true);
//        if (permissionsHelper.checkAllPermissions(permissions())) {
        permissionsHelper = new PermissionsHelper(mActivity, setPermissions(), true);
        if (permissionsHelper.checkAllPermissions(setPermissions())) {
            permissionsHelper.onDestroy();
            setDate();
        } else {
            //申请权限
            permissionsHelper.startRequestNeedPermissions();
            permissionsHelper.setParams(setDialogInfo(new DialogInfo()));
        }
        permissionsHelper.setonAllNeedPermissionsGrantedListener(new PermissionsHelper.onAllNeedPermissionsGrantedListener() {
            //全部许可了,已经获得了所有权限
            @Override
            public void onAllNeedPermissionsGranted() {
                //做原先的业务代码
                setDate();
            }

            //被拒绝了,只要有一个权限被拒绝那么就会调用
            @Override
            public void onPermissionsDenied() {
                //拒绝了,如何处理?(视情况而定)
                JLog.d();
                permissionsHelper.setParams(null);
            }

            //用户已经永久的拒绝了
            @Override
            public void hasLockForever() {
                JLog.i("hasLockForever");
                permissionsHelper.setParams(null);
            }

            //被拒绝后,在最后一次申请权限之前
            @Override
            public void onBeforeRequestFinalPermissions(PermissionsHelper helper) {
                JLog.i();
                helper.continueRequestPermissions();
            }
        });
    }

    protected abstract void setDate();

    private DialogInfo setDialogInfo(DialogInfo dialogInfo) {
        dialogInfo.setTitle("权限不足");
        dialogInfo.setContent("需要必须的权限才能正常使用本应用");
        dialogInfo.setPositiveButtonText("去设置");
        dialogInfo.setNegativeButtonText("取消");
        dialogInfo.showDialog(true);
        return dialogInfo;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionsHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        permissionsHelper.onActivityResult(requestCode, resultCode, data);
    }
}
