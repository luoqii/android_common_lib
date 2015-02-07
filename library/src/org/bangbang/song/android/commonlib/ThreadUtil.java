package org.bangbang.song.android.commonlib;

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
    
    public static void catchCrashAndShow(final Application app) {
        final UncaughtExceptionHandler original = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                PrintStream writer;
                File crashFile = null;
                // "00" for see this file at first glance .
                String name = "00_" + app.getPackageName() + "_crash.log.txt";
                try {
                    crashFile = app.getFileStreamPath(name);
                    crashFile.delete();
                    app.openFileOutput(name, Context.MODE_WORLD_READABLE);
                    crashFile = app.getFileStreamPath(name);
                    crashFile.createNewFile();
                    
                    writer = new PrintStream(crashFile);
                    writer.append("crash at: " + new Date().toString());
                    writer.append("\n");
                    writer.flush();
                    
                    ex.printStackTrace(writer);
                    ex.printStackTrace();
                    writer.flush();
                    writer.close();
                    
                    Intent view = new Intent(Intent.ACTION_VIEW);
                    view.setDataAndType(Uri.fromFile(crashFile), "text/*");
                    view.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    view.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    app.startActivity(view);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                original.uncaughtException(thread, ex);
            }
        });
    }
    
    /**
     * yes, generate a NPE for debug.
     */
    public static void npe(){
    	String npe = null;
    	if (npe.isEmpty()) {
    		npe = "";
    	}
    }
}
