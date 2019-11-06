package com.sophiemarceauqu.lib_video.app;

import android.content.Context;

// 用来为调用者创建视频
public final class VideoHelper {
    //SDK全局Context
    private static Context mContext;

    private static void init(Context context) {
        mContext = context;
        //初始化SDK的时候，初始化realm数据库
    }

    public static Context getContent() {
        return mContext;
    }
}
