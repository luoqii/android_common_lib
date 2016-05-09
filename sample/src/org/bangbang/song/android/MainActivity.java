package org.bangbang.song.android;

import org.bangbang.song.android.commonlib.R;
import org.bangbang.song.android.commonlib.demo.BuildConfig;
import org.bbs.android.commonlib.activity.BaseApiDemo;
import org.bbs.android.log.Logcat_FragmentActivity;

import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends BaseApiDemo {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected String getInitPathPrefix() {
        return "androidcommonlib";
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (BuildConfig.DEBUG) {
            menu.add(0, R.id.android_comm_lib_menu_logcat, 0, "Logcat");
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.android_comm_lib_menu_logcat:
                Logcat_FragmentActivity.start(this);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
