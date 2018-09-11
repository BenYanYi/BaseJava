package com.mylove.basejava;

import android.os.Environment;

import com.mylove.baselib.activity.permission.BasePermissionsActivity;
import com.mylove.loglib.JLog;
import com.mylove.okhttp.OkHttpUtil;
import com.mylove.okhttp.OnDownloadListener;
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
        String url = "http://www.yanyi.red/bluetooth/dfu_pkg0904.zip";
        String filePath = Environment.getExternalStorageDirectory().toString() + "/dectector/dfu/";
        JLog.init(true);
        JLog.d(filePath);
        OkHttpUtil.getInstance(mContext).downloadFile(url).download(filePath, new OnDownloadListener() {
            @Override
            public void onDownloading(int progress) {
                JLog.d("进度" + progress + "%");
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public <T> void onSuccess(T message) {
                JLog.i("文件地址" + message);
            }

            @Override
            public void onFailure(Throwable t) {
                JLog.e(t.getMessage());
            }
        });
    }

    @Override
    protected int setLayoutID() {
        return R.layout.main_activity;
    }
}
