package org.bangbang.song.android;

import org.bangbang.song.android.commonlib.ContextUtil;
import org.bangbang.song.android.commonlib.CrazyClicker;
import org.bangbang.song.android.commonlib.CrazyClicker.Callback;
import org.bangbang.song.android.commonlib.R;
import org.bangbang.song.android.commonlib.activity.LogActivity;
import org.bangbang.song.android.commonlib.activity.LogcatActivity;
import org.bangbang.song.android.commonlib.demo.BuildConfig;

import android.content.Intent;
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
        
        setContentView(R.layout.activity_main);
        mCrazyClicker = new CrazyClicker(new Callback() {
            
            @Override
            public void onFireInTheHole() {
                Intent intent = new Intent(DemoCrazyClicker.this, LogcatActivity.class);
                startActivity(intent);
            }
        });
        
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
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_MENU == keyCode) {
            mCrazyClicker.onClick();
        }
        return super.onKeyDown(keyCode, event);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (MotionEvent.ACTION_DOWN == event.getAction()) {
            mCrazyClicker.onClick();
        }
        return super.onTouchEvent(event);
    }
}
