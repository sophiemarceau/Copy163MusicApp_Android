package com.sophiemarceauqu.lib_pullalive.app;


import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

/**
 * 一个轻量级后台Job Service，利用空闲时间执行一些小事，提供进程不被回收的概率
 */
@TargetApi(value = Build.VERSION_CODES.LOLLIPOP)
public class AliveJobService extends JobService {
    private static final String TAG = AliveJobService.class.getName();
    private static final int PULL_ALIVE = 0x01;
    private JobScheduler mJobScheduler;
    private Handler mJobHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            Log.d(TAG, "pull alive.");
            jobFinished((JobParameters) message.obj, true);
            return true;
        }
    });
//    private Handler mJobHandler = new Handler(Looper.getMainLooper()){
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//             switch (msg.what){
//                case PULL_ALIVE: Log.d(TAG, "pull alive.");
//                jobFinished((JobParameters) msg.obj, true);
//            }
//        }
//    };

    public static void start(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Intent intent = new Intent(context, AliveJobService.class);
            context.startService(intent);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        JobInfo job = initJobInfo(startId);
        //提交自己的job 到system process中
        if (mJobScheduler.schedule(job) <= 0) {
            Log.d(TAG, "AliveJobService failed");
        } else {
            Log.d(TAG, "AliveJobService success");
        }
        return START_STICKY;
    }

    //开始任务
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        mJobHandler.sendMessage(Message.obtain(mJobHandler, 1, jobParameters));
        return true;
    }

    //结束任务
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        mJobHandler.sendEmptyMessage(1);
        return false;
    }

    private JobInfo initJobInfo(int startId) {
        JobInfo.Builder builder = new JobInfo.Builder(startId,
                new ComponentName(getPackageName(), AliveJobService.class.getName()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setMinimumLatency(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS)//执行最小延迟时间
                    .setOverrideDeadline(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS)//执行最长延时时间
                    .setBackoffCriteria(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS,
                            JobInfo.BACKOFF_POLICY_EXPONENTIAL);//线性重试方案

        } else {
            builder.setPeriodic(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS);
        }
        builder.setPersisted(false);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE);
        builder.setRequiresCharging(false);
        return builder.build();
    }
}
