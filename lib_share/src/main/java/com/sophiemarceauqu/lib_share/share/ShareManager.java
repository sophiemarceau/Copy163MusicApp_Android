package com.sophiemarceauqu.lib_share.share;


import android.content.Context;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 分享功能统一入口，负责发送数据到指定平台，可以优化为一个单例模式
 */
public class ShareManager implements PlatformActionListener{
    /**
     * 要分享到的平台
     */
    private Platform mCurrentPlatform;

    private PlatformShareListener mListener;

    //单例方法
    private static class SingletonHolder {
        private static ShareManager instance = new ShareManager();
    }

    public static ShareManager getInstance() {
        return ShareManager.SingletonHolder.instance;
    }
//    /**
//     * 线程安全的单例模式
//     */
//    public static ShareManager getInstance() {
//        if (mShareManager == null) {
//            synchronized (ShareManager.class) {
//                if (mShareManager == null) {
//                    mShareManager = new ShareManager();
//                }
//            }
//        }
//        return mShareManager;
//    }

    private ShareManager() {
    }

    /**
     * 第一个执行的方法，最好在程序入口执行
     *
     * @param context
     */
    public static void initSDK(Context context) {
        ShareSDK.initSDK(context);
    }

    /**
     * 分享数据到不同平台
     * @param shareData
     * @param listener
     */
    public void shareData(ShareData shareData, PlatformShareListener listener) {
        mListener = listener;
        switch (shareData.mPlatformType) {
            case QQ:
                mCurrentPlatform = ShareSDK.getPlatform(QQ.NAME);
                break;
            case QZone:
                mCurrentPlatform = ShareSDK.getPlatform(QZone.NAME);
                break;
            case WeChat:
                mCurrentPlatform = ShareSDK.getPlatform(Wechat.NAME);
                break;
            case WechatMoments:
                mCurrentPlatform = ShareSDK.getPlatform(WechatMoments.NAME);
                break;
            default:
                break;
        }
        mCurrentPlatform.setPlatformActionListener(this);
        mCurrentPlatform.share(shareData.mShareParams);
    }


    /**
     * 应用程序需要的平台
     */
    public enum PlatformType {
        QQ, QZone, WeChat, WechatMoments;
    }

    //与业务层的接口
    public interface PlatformShareListener{
        void onComplete(int var2, HashMap<String, Object> var3);

        void onError(int var2, Throwable var3);

        void onCancel(int var2);
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        //分享完成回调
        if(mListener!=null){
            mListener.onComplete(i,hashMap);
        }

    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        if(mListener!=null) {
            mListener.onError(i, throwable);
        }
    }

    @Override
    public void onCancel(Platform platform, int i) {
        //分项取消
        if(mListener!=null){
            mListener.onCancel(i);
        }
    }
}
