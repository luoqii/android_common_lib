package org.bbs.android.commonlib;

import java.io.File;
import java.io.PrintStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Date;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class ThreadUtil {
    private static final String TAG = ThreadUtil.class.getSimpleName();
    
    public static void sleepSafely(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            ;; // silent is golden.
        }
    }
}
