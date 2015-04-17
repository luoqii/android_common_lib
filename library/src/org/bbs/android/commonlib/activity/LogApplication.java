package org.bbs.android.commonlib.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Application;
import android.os.Build;
import android.util.Log;

public class LogApplication extends Application {
    private static final String TAG = LogApplication.class.getSimpleName();
    
    protected boolean needLogLifecycle() {
        return true;
    }
    protected boolean needLogMemory() {
        return false;
    }
    protected void log(String message) {
        Log.d(getClass().getSimpleName(), message);
    }
    
    // life cycle.
    @Override
    public void onCreate() {
        super.onCreate();
        
        if (needLogLifecycle()){
            log("onCreate(). ");
        }
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        
        if (needLogLifecycle()){
            log("onCreate(). ");
        }
    }
    
    // memory
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        
        if (needLogMemory()) {
            log("onLowMemory().");
        }
    }
    //lint @Override
    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        
        if (needLogMemory()) {
            log("onTrimMemory(). level: " + level);
        }
    }
}
