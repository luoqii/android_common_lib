package org.bangbang.song.android;

import org.bbs.android.commonlib.ExceptionCatcher;
import org.bbs.android.commonlib.activity.LogApplication;
import org.bbs.android.log.Log;


public class Application extends LogApplication {
    private static final String TAG = Application.class.getSimpleName();
    
    @Override
    public void onCreate() {
        super.onCreate();
        
//        ThreadUtil.sleepSafely(8 * 1000);
        Log.d(TAG, "onCreate");
        
        ExceptionCatcher.attachExceptionHandler(this);
//        ExceptionCatcher.npe();

    }
}
