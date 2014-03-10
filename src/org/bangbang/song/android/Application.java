package org.bangbang.song.android;

import org.bangbang.song.android.commonlib.ThreadUtil;


public class Application extends android.app.Application {
    private static final String TAG = Application.class.getSimpleName();
    
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        
//        ThreadUtil.sleepSafely(5 * 1000);
    }
}
