package com.mylove.baselib.activity.permission;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.mylove.baselib.activity.BaseActivity;
import com.yanyi.permissionlib.PermissionDialogInfo;
import com.yanyi.permissionlib.PermissionHelper;


/**
 * @author yanyi
 * get permissions activity
 */

public abstract class BasePermissionsActivity extends BaseActivity {
    private PermissionHelper permissionHelper;

    /**
     * permission to get
     *
     * @return permissions
     */
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

    /**
     * get permissions success
     */
    protected abstract void allPermissionSuccess();

    /**
     * get permissions failure
     */
    public void allPermissionFailure() {
        finish();
    }

    /**
     * prompt popup when getting permission
     *
     * @return null
     */
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
