package org.bangbang.song.android;

import org.bangbang.song.android.commonlib.demo.BuildConfig;
import org.bangbang.song.android.commonlib.demo.R;
import org.bbs.android.commonlib.ContextUtil;
import org.bbs.android.commonlib.CrazyClicker;
import org.bbs.android.commonlib.CrazyClicker.Callback;
import org.bbs.android.commonlib.activity.LogActivity;
import org.bbs.android.log.Logcat_FragmentActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class DemoCrazyClicker extends LogActivity {
    private static final String TAG = DemoCrazyClicker.class.getSimpleName();
    // public for test only.
    public boolean mPaused;
    private CrazyClicker mCrazyClicker;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.android_comm_lib_activity_main);
        mCrazyClicker = new CrazyClicker(new Callback() {
            
            @Override
            public void onFireInTheHole() {
                Logcat_FragmentActivity.start(DemoCrazyClicker.this);
            }
        });
        
        if (BuildConfig.DEBUG) {
        	ContextUtil.disableKeyguard(this);
        }
    }    
    
    public void onUserInteraction() {
        if (BuildConfig.DEBUG) {
            mCrazyClicker.onClick();
        }
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	
    	mPaused = false;
    }
    
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	
    	mPaused = true;
    }
}
