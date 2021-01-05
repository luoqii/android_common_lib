package org.bangbang.song.android;

import org.bangbang.song.android.commonlib.demo.BuildConfig;
import org.bangbang.song.android.commonlib.demo.R;
import org.bbs.android.commonlib.AndroidUtil;
import org.bbs.android.commonlib.activity.BaseApiDemo;
import org.bbs.android.commonlib.activity.LogActivity;
import org.bbs.android.log.Logcat_FragmentActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class UtilActivity extends LogActivity {
    private static final String TAG = UtilActivity.class.getSimpleName();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_util);
    }   
    
    public void onClick(View view){
    	switch (view.getId()){
    	case R.id.poweroff:
    		AndroidUtil.poweroff();
    		break;	
    	case R.id.restart:
    		AndroidUtil.restart(this);
    		break;
    	}
    }

    public static class MainActivity extends BaseApiDemo {
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
}
