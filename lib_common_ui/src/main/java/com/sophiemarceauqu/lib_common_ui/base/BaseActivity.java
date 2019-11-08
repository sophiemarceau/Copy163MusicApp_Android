package com.sophiemarceauqu.lib_common_ui.base;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.sophiemarceauqu.lib_common_ui.base.constant.Constant;
import com.sophiemarceauqu.lib_common_ui.utils.StatusBarUtil;

public class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //沉浸式的效果 就是 在状态栏里面的 文字 电池 信号之类的都是黑色的
        StatusBarUtil.statusBarLightMode(this);
    }


    //申请指定的权限
    public void requestPermission(int code, String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, code);
    }

    //判断是否有指定的权限
    public boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case Constant.WRITE_READ_EXTERNAL_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doSDCardPermission();
                }
                break;
            case Constant.HARDWEAR_CAMERA_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doCameraPermission();
                }
                break;
        }
    }

    //处理整个应用中的SDCard业务
    public void doSDCardPermission() {
    }

    public void doCameraPermission() {
    }
}
