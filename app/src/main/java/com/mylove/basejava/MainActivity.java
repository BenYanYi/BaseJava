package com.mylove.basejava;

import com.mylove.baselib.activity.permission.BasePermissionsActivity;
import com.mylove.baselib.activity.permission.PermissionType;

/**
 * @author myLove
 * @date 2018-05-08 15:04
 * @e-mail love@yanyi.red
 * @overview
 */
public class MainActivity extends BasePermissionsActivity {
    @Override
    protected String[] setPermissions() {
        return new String[]{PermissionType.STORAGE};
    }

    @Override
    protected void setDate() {

    }

    @Override
    protected int setLayoutID() {
        return R.layout.main_activity;
    }
}
