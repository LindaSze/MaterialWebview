package com.lynnsze.materialwebview.base;


import android.app.Application;
import android.os.StrictMode;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class MWApplication extends Application {
    private static MWApplication mInstance;

    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;

        if(MWConfig.isDebug)
        checkApp();
    }

    private  void checkApp(){
        mRefWatcher = LeakCanary.install(this);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectCustomSlowCalls()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyDialog()
                .penaltyLog()
                .penaltyFlashScreen()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }

    public static RefWatcher getRefWatcher() {
        return mInstance.mRefWatcher;
    }
}
