package org.bangbang.song.android;
import org.bangbang.song.android.demo.BaseApiDemo;

import android.app.Activity;


public class MainActivity extends BaseApiDemo {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected String getInitPathPrefix() {
        return "androidcommonlib";
    }
}
