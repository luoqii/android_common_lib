package org.bangbang.song.android.commonlib;

public class ThreadUtil {
    private static final String TAG = ThreadUtil.class.getSimpleName();
    
    public static void sleepSafely(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            ;; // slient is golden.
        }
    }
}
