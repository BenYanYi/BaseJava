package com.mylove.basejava;

import com.mylove.baselib.activity.permission.BasePermissionsActivity;
import com.yanyi.permissionlib.PermissionType;

/**
 * @author myLove
 */
public class MainActivity extends BasePermissionsActivity {
    @Override
    protected String[] setPermissions() {
        return new String[]{PermissionType.STORAGE};
    }

    @Override
    protected void allPermissionSuccess() {

    }

    @Override
    protected int setLayoutID() {
        return R.layout.main_activity;
    }
}
