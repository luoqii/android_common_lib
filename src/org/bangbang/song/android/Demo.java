package org.bangbang.song.android;

import org.bangbang.song.android.commonlib.BuildConfig;
import org.bangbang.song.android.commonlib.ContextUtil;
import org.bangbang.song.android.commonlib.R;
import org.bangbang.song.android.commonlib.activity.LogActivity;

import android.os.Bundle;

public class Demo extends LogActivity {
    private static final String TAG = Demo.class.getSimpleName();
    // public for test only.
    public boolean mPaused;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        
        if (BuildConfig.DEBUG) {
        	ContextUtil.disableKeyguard(this);
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
