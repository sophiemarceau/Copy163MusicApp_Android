package com.sophiemarceauqu.lib_network.okhttp.response.listener;

/**
 * @function 监听下载进度
 */
public interface DisposeDownloadListener extends DisposeDataListener {
    void onProgress(int progress);
}