package org.bangbang.song.android;

import org.bangbang.song.android.commonlib.R;

import android.app.Activity;
import android.os.Bundle;

public class Demo extends Activity {
    private static final String TAG = Demo.class.getSimpleName();
    // public for test only.
    public boolean mPaused;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
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
