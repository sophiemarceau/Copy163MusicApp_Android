package com.sophiemarceauqu.lib_audio.mediaplayer.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import com.sophiemarceauqu.lib_audio.R;
import com.sophiemarceauqu.lib_audio.mediaplayer.core.AudioController;
import com.sophiemarceauqu.lib_audio.mediaplayer.core.CustomMediaPlayer;
import com.sophiemarceauqu.lib_audio.mediaplayer.db.GreenDaoHelper;
import com.sophiemarceauqu.lib_audio.mediaplayer.events.AudioFavouriteEvent;
import com.sophiemarceauqu.lib_audio.mediaplayer.events.AudioLoadEvent;
import com.sophiemarceauqu.lib_audio.mediaplayer.events.AudioPauseEvent;
import com.sophiemarceauqu.lib_audio.mediaplayer.events.AudioPlayModeEvent;
import com.sophiemarceauqu.lib_audio.mediaplayer.events.AudioProgressEvent;
import com.sophiemarceauqu.lib_audio.mediaplayer.events.AudioStartEvent;
import com.sophiemarceauqu.lib_audio.mediaplayer.model.AudioBean;
import com.sophiemarceauqu.lib_audio.mediaplayer.utils.Utils;
import com.sophiemarceauqu.lib_common_ui.base.BaseActivity;
import com.sophiemarceauqu.lib_image_loader.app.ImageLoaderManager;
import com.sophiemarceauqu.lib_share.share.ShareDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 *播放音乐Activity
 */

public class MusicPlayerActivity extends BaseActivity {
    private RelativeLayout mBgView;
    private TextView mInfoView;
    private TextView mAuthorView;
    private ImageView mFavouriteView;

    private SeekBar mProgressView;
    private TextView mStartTimeView;
    private TextView mTotalTimeView;

    private ImageView mPlayModeView;
    private ImageView mPlayView;
    private ImageView mNextView;
    private ImageView mPreViousView;

    private Animator animator;

    //data
    private AudioBean mAudioBean;//当前正在播放歌曲
    private AudioController.PlayMode mPlayMode;

    public static void start(Activity context){
        Intent intent = new Intent(context,MusicPlayerActivity.class);
        ActivityCompat.startActivity(context,intent, ActivityOptionsCompat.makeSceneTransitionAnimation(context).toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //添加入场动画
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.transition_bottom2top));
        }
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_music_service_layout);
        initData();
        initView();
    }

    private void initData() {
        mAudioBean = AudioController.getInstance().getNowPlaying();
        mPlayMode = AudioController.getInstance().getPlayMode();
    }

    private void initView() {
        mBgView = findViewById(R.id.root_layout);
        ImageLoaderManager.getInstance().displayImageForViewGroup(mBgView,mAudioBean.albumPic);
        findViewById(R.id.back_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
        findViewById(R.id.title_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.share_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareMusic(mAudioBean.mUrl,mAudioBean.name);
            }
        });
        findViewById(R.id.show_list_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicListDialog dialog = new MusicListDialog(MusicPlayerActivity.this);
                dialog.show();
            }
        });
        mInfoView = findViewById(R.id.album_view);
        mInfoView.setText(mAudioBean.albumInfo);
        mInfoView.requestFocus();
        mAuthorView = findViewById(R.id.auther_view);
        mAuthorView.setText(mAudioBean.author);

        mFavouriteView = findViewById(R.id.favourite_view);
        mFavouriteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //收藏 与否
                AudioController.getInstance().changeFavourite();
            }
        });
        changeFavouriteStatus(false);
        mStartTimeView = findViewById(R.id.start_time_view);
        mTotalTimeView = findViewById(R.id.total_time_view);
        mProgressView = findViewById(R.id.progress_view);
        mProgressView.setProgress(0);
        mProgressView.setEnabled(false);

        mPlayModeView = findViewById(R.id.play_mode_view);
        mPlayModeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //切换播放模式
                switch (mPlayMode){
                    case RANDOM:
                        AudioController.getInstance().setPlayMode(AudioController.PlayMode.REPEAT);
                        break;
                    case LOOP:
                        AudioController.getInstance().setPlayMode(AudioController.PlayMode.RANDOM);
                        break;
                    case REPEAT:
                        AudioController.getInstance().setPlayMode(AudioController.PlayMode.LOOP);
                        break;
                }
            }
        });
        updatePlayModeView();
        mPreViousView = findViewById(R.id.previous_view);
        mPreViousView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioController.getInstance().previous();
            }
        });
        mPlayView = findViewById(R.id.play_view);
        mPlayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioController.getInstance().playOrPause();
            }
        });
        mNextView = findViewById(R.id.next_view);
        mNextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioController.getInstance().next();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAudioLoadEvent(AudioLoadEvent event){
        //更新notification为load状态
        mAudioBean = event.mAudioBean;
        ImageLoaderManager.getInstance().displayImageForViewGroup(mBgView,mAudioBean.albumPic);
        //可以与初始化时封装一个方法
        mInfoView.setText(mAudioBean.albumInfo);
        mAuthorView.setText(mAudioBean.author);
        changeFavouriteStatus(false);
        mProgressView.setProgress(0);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAudioPauseEvent(AudioPauseEvent event){
        //更新Activity为暂停
        showPauseView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAudioStartEvent(AudioStartEvent event){
        showPlayView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAudioFavouriteEvent(AudioFavouriteEvent event){
        //更新Activity收藏状态
        changeFavouriteStatus(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAudioPlayModeEvent(AudioPlayModeEvent event){
        mPlayMode = event.mPlayMode;
        //更新播放模式
        updatePlayModeView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAudioProgressEvent(AudioProgressEvent event){
        int totalTIme = event.maxLength;
        int currentTime = event.progress;
        //更新时间
        mStartTimeView.setText(Utils.formatTime(currentTime));
        mTotalTimeView.setText(Utils.formatTime(totalTIme));
        mProgressView.setProgress(currentTime);
        mProgressView.setMax(totalTIme);
        if (event.mStatus == CustomMediaPlayer.Status.PAUSED){
            showPauseView();
        }else{
            showPlayView();
        }
    }

    private void showPlayView() {
        mPlayView.setImageResource(R.mipmap.audio_aj6);
    }

    private void showPauseView() {
        mPlayView.setImageResource(R.mipmap.audio_aj7);
    }

    //分享好友
    private void shareMusic(String mUrl, String name) {
        ShareDialog dialog = new ShareDialog(this,false);
        dialog.setShareTitle(name);
        dialog.setShareType(5);
        dialog.setShareTitleUrl(mUrl);
        dialog.setShareText("苏菲玛索");
        dialog.setShareSite("Sophie Marceau");
        dialog.setShareSiteUrl("http://www.apple.com");
        dialog.show();
    }

    private void changeFavouriteStatus(boolean anim){
        if (GreenDaoHelper.selectFavourite(mAudioBean)!=null){
            mFavouriteView.setImageResource(R.mipmap.audio_aeh);
        }else {
            mFavouriteView.setImageResource(R.mipmap.audio_aef);
        }

        if (anim){
            if (animator!=null){
                //留个作业 将动画封到View中作为一个自定义View
                animator.end();
            }else{
                PropertyValuesHolder animX = PropertyValuesHolder.ofFloat(View.SCALE_X.getName(),1.0f,1.2f,1.0f);
                PropertyValuesHolder animY = PropertyValuesHolder.ofFloat(View.SCALE_Y.getName(),1.0f,1.2f,1.0f);
                animator = ObjectAnimator.ofPropertyValuesHolder(mFavouriteView,animX,animY);
                animator.setInterpolator(new AccelerateInterpolator());
                animator.setDuration(300);
                animator.start();
            }
        }
    }
    private  void updatePlayModeView(){
        switch (mPlayMode){
            case LOOP:
                mPlayModeView.setImageResource(R.mipmap.player_loop);
                break;
            case RANDOM:
                mPlayModeView.setImageResource(R.mipmap.player_random);
                break;
            case REPEAT:
                mPlayModeView.setImageResource(R.mipmap.player_once);
                break;
        }
    }
}
