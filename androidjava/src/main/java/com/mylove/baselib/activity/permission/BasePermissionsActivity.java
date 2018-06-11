package com.mylove.baselib.activity.permission;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.mylove.baselib.activity.BaseActivity;
import com.yanyi.permissionlib.PermissionDialogInfo;
import com.yanyi.permissionlib.PermissionHelper;


/**
 * @author myLove
 * 获取权限activity
 */

public abstract class BasePermissionsActivity extends BaseActivity {
    private PermissionHelper permissionHelper;

    protected abstract String[] setPermissions();

    @Override
    protected void init() {
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

    protected abstract void allPermissionSuccess();

    public void allPermissionFailure() {
        finish();
    }

    public PermissionDialogInfo setDialogInfo() {
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        permissionHelper.onActivityResult(requestCode, resultCode, data);
    }
}
