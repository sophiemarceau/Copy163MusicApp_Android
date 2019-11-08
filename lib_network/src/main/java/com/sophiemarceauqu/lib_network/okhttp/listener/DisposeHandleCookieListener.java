package com.sophiemarceauqu.lib_network.okhttp.listener;

import java.util.ArrayList;

/**
 * 需要专门处理Cookie时创建此回调接口
 */
public interface DisposeHandleCookieListener extends DisposeDataListener
{
    public void onCookie(ArrayList<String> cookieStrLists);
}
