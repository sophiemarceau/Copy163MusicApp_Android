package com.sophiemarceau_qu.lib_base.service.audio;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * 音频对外接口
 */
public interface AudioService extends IProvider {
    void pauseAudio();

    void resumeAudio();
}
