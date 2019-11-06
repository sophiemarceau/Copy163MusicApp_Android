package com.sophiemarceauqu.qxb_voice.view.home.adpater;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.sophiemarceauqu.qxb_voice.view.discovery.DiscoveryFragment;
import com.sophiemarceauqu.qxb_voice.view.friend.FriendFragment;
import com.sophiemarceauqu.qxb_voice.view.home.model.CHANNEL;
import com.sophiemarceauqu.qxb_voice.view.mine.MineFragment;

public class HomePagerAdapter extends FragmentPagerAdapter {
    private CHANNEL[] mList;

    public HomePagerAdapter(FragmentManager fm, CHANNEL[] datas) {
        super(fm);
        mList = datas;
    }


    @Override
    public Fragment getItem(int i) {
        int type = mList[i].getValue();
        switch (type) {
            case CHANNEL.MINE_ID:
                return MineFragment.newInstance();
            case CHANNEL.DISCORY_ID:
                return DiscoveryFragment.newInstance();
            case CHANNEL.FRIEND_ID:
                return FriendFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.length;
    }
}
