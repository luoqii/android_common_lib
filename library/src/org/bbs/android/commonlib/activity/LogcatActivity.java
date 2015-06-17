
package org.bbs.android.commonlib.activity;

import org.bbs.android.commonlib.activity.LogcatFragment.FilterSpec;

import android.Manifest.permission;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
//import org.bangbang.song.android.commonlib.R;
//import org.bangbang.song.android.commonlib.FileHierachySpec;

/**
 * android logcat application output log viewer.
 * 
 * <pre>
 * need permission android.permission.READ_LOGS
 * need permission android.permission.WRITE_EXTERNAL_STORAGE if you do not give a 
 * log dir in {@link #EXTRA_LOG_SAVE_DIR}, or the dir you give live on SDCard.
 * 
 * 
 * @TODO how to see system log???
 * 
 * @author bysong@tudou.com
 * 
 * @see {@link permission#READ_LOGS}
 */
public class LogcatActivity extends 
//                                   LogActivity 
                                   FragmentActivity
{
    private static final String TAG = LogcatActivity.class.getSimpleName();

    public static void start(Context context){
    	Intent logcat = new Intent(context, LogcatActivity.class);
    	context.startActivity(logcat);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(getResources().getIdentifier("android_comm_lib_logcat", "layout", getPackageName()));

        Fragment logcat = new LogcatFragment();
        setArgs(logcat, getIntent());
        
        getSupportFragmentManager().beginTransaction().add(getResources().getIdentifier("logcat_container", "id", getPackageName()), logcat).commit();
  
		if (viewServerEnabled()) {
		    ViewServer.get(this).addWindow(this);
		}
    }

    private boolean viewServerEnabled() {
		return false;
	}

	private void setArgs(Fragment fragment, Intent intent) {
        if (null == intent) {
            return;
        }

        String mLogSaveDir = intent.getStringExtra(LogcatFragment.EXTRA_LOG_SAVE_DIR);

        int mLogLimit = intent.getIntExtra(LogcatFragment.EXTRA_LOG_LIMIT, LogcatFragment.DEFAULT_LOG_LIMIT);
        
        Parcelable p = intent.getParcelableExtra(LogcatFragment.EXTRA_FILTER_SPEC);
        if (null == p) {
            Log.w(TAG, "no filter in intent, ignore.");
        }
        
        LogcatFragment.FilterSpec spec = (LogcatFragment.FilterSpec) p;
        
        Bundle args = new Bundle();
        args.putString(LogcatFragment.EXTRA_LOG_SAVE_DIR, mLogSaveDir);
        args.putInt(LogcatFragment.EXTRA_LOG_LIMIT, mLogLimit);
        if (null != p ) {
        	args.putParcelable(LogcatFragment.EXTRA_FILTER_SPEC, spec);
        }
        
        fragment.setArguments(args);
    }
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if (viewServerEnabled()) {
		    ViewServer.get(this).setFocusedWindow(this);
		}
	}
    
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		if (viewServerEnabled()) {
		    ViewServer.get(this).removeWindow(this);
		}
	}
   
}
