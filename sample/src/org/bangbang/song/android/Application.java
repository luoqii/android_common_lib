package org.bangbang.song.android;

import org.bbs.android.commonlib.ExceptionCatcher;
import org.bbs.android.commonlib.Log;
import org.bbs.android.commonlib.Logger;
import org.bbs.android.commonlib.activity.LogApplication;


public class Application extends LogApplication {
    private static final String TAG = Application.class.getSimpleName();
    
    @Override
    public void onCreate() {
        super.onCreate();
        
//        ThreadUtil.sleepSafely(8 * 1000);
        Log.d(TAG, "onCreate");
        Logger.d(TAG, "onCreate");
        
        ExceptionCatcher.attachExceptionHandler(this);
//        ExceptionCatcher.npe();
        
    }
}
