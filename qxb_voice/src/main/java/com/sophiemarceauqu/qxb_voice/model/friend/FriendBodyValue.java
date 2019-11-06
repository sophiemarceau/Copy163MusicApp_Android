package com.sophiemarceauqu.qxb_voice.model.friend;

import com.sophiemarceauqu.lib_audio.mediaplayer.model.AudioBean;
import com.sophiemarceauqu.qxb_voice.model.BaseModel;

import java.util.ArrayList;

public class FriendBodyValue extends BaseModel {
    public int type;
    public String avatr;
    public String name;
    public String fans;
    public String text;
    public String videoUr;
    public ArrayList<String> pics;
    public String zan;
    public String msg;
    public AudioBean audioBean;
}
