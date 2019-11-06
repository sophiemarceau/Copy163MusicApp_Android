package com.sophiemarceauqu.qxb_voice.view.friend;

import android.content.Context;

import com.sophiemarceauqu.lib_common_ui.recyclerview.MultiItemTypeAdapter;
import com.sophiemarceauqu.qxb_voice.model.friend.FriendBodyValue;

import java.util.List;

public class FriendRecyclerAdapter extends MultiItemTypeAdapter {
    public static  final  int MUSIC_TYPE = 0x01;//音乐类型
    public static  final  int VIDEO_TYPE = 0x02;//视频类型

    private Context mContext;

    public FriendRecyclerAdapter(Context context, List<FriendBodyValue> datas){
        super(context,datas);
    }
}
