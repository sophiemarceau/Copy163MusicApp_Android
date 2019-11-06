package com.sophiemarceauqu.qxb_voice.view.loading;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.sophiemarceauqu.lib_common_ui.base.BaseActivity;
import com.sophiemarceauqu.lib_common_ui.base.constant.Constant;
import com.sophiemarceauqu.lib_pullalive.app.AliveJobService;
import com.sophiemarceauqu.qxb_voice.R;
import com.sophiemarceauqu.qxb_voice.view.home.HomeActivity;

public class LoadingActivity extends BaseActivity {

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            startActivity(new Intent(LoadingActivity.this, HomeActivity.class));
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        avoidLaunchAgain();
        setContentView(R.layout.activity_loading_layout);
        pullAliveService();
        if (hasPermission(Constant.WRITE_READ_EXTERNAL_PERMISSION)) {
            doSDCardPermission();
        } else {
            requestPermission(Constant.WRITE_READ_EXTERNAL_CODE, Constant.WRITE_READ_EXTERNAL_PERMISSION);
        }
    }

    @Override
    public void doSDCardPermission() {
        mHandler.sendEmptyMessageDelayed(0, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    private void avoidLaunchAgain() {
        //避免从桌面启动程序后，会重新实例化入口类的Activity
        if (!this.isTaskRoot()) {//判断当前Activity是不是在所在的任务栈的根
            Intent intent = getIntent();
            if (intent != null) {
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                    finish();
                }
            }
        }
    }

    private void pullAliveService() {
        AliveJobService.start(this);
    }

}
