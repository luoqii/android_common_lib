package org.bangbang.song.android;

import org.bangbang.song.android.commonlib.ThreadUtil;
import org.bangbang.song.android.commonlib.activity.LogApplication;


public class Application extends LogApplication {
    private static final String TAG = Application.class.getSimpleName();
    
    @Override
    public void onCreate() {
        super.onCreate();
        
//        ThreadUtil.sleepSafely(8 * 1000);
        ThreadUtil.catchCrashAndShow(this);
        
//        npe();
    }

    private void npe() {
        String nullString = null;
        if (nullString.length() == 0) {
            
        }
    }
}
