package com.sophiemarceauqu.lib_update.update.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class Utils {
    public static int getVersionCode(Context context) {
        int versionCode = 1;
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;

    }
}
