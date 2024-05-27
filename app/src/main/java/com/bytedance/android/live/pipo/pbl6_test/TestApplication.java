package com.bytedance.android.live.pipo.pbl6_test;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.work.Configuration;
import com.bytedance.android.live.pipo.pbl6_test.utils.AppContextManager;

import dagger.hilt.android.HiltAndroidApp;

/**
 * Created by NatalieWong on 23/5/24
 *
 * @author nat.wong@bytedance.com
 */
@HiltAndroidApp
public class TestApplication extends Application implements  androidx.work.Configuration.Provider {
    String LogTag = "JobInfoScheduler";

    public TestApplication(){
        super();
        Log.d(LogTag, "TestApplication: init");
        AppContextManager.INSTANCE.init(this);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LogTag, "TestApplication: onCreate");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Boolean isMainThread = Looper.getMainLooper().isCurrentThread();
        Log.d(LogTag, "TestApplication: attachBaseContext");
        Log.d(LogTag, "TestApplication: mainThread: "+isMainThread);
        Log.d(LogTag, "TestApplication: attachBaseContext "+ this.getPackageName());
    }

    @NonNull
    @Override
    public Configuration getWorkManagerConfiguration() {
        return new androidx.work.Configuration.Builder()
                .setMinimumLoggingLevel(Log.DEBUG)
                .build();
    }
}
