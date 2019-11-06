package com.sophiemarceauqu.lib_audio.mediaplayer.events;

import com.sophiemarceauqu.lib_audio.mediaplayer.core.AudioController;

public class AudioPlayModeEvent {
    public AudioController.PlayMode mPlayMode;

    public AudioPlayModeEvent(AudioController.PlayMode playMode){
        this.mPlayMode = playMode;
    }
}
