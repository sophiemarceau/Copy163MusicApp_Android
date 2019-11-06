package com.sophiemarceauqu.lib_audio.mediaplayer.exception;

public class AudioQueueEmptyException extends RuntimeException {
    public AudioQueueEmptyException(String error) {
        super(error);
    }
}
