package com.sophiemarceauqu.lib_audio.app.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sophiemarceau_qu.lib_base.service.audio.AudioService;
import com.sophiemarceauqu.lib_audio.mediaplayer.core.AudioController;

/**
 * AudioService实现类
 */
@Route(path = "/audio/audio_service")
public class AudioServiceImpl implements AudioService {
    @Override
    public void pauseAudio() {
        AudioController.getInstance().pause();
    }

    @Override
    public void resumeAudio() {
        AudioController.getInstance().resume();
    }

    @Override
    public void init(Context context) {

    }
}
