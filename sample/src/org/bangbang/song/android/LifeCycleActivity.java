package org.bangbang.song.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.bangbang.song.android.commonlib.demo.R;
import org.bbs.android.commonlib.AndroidUtil;
import org.bbs.android.commonlib.activity.LogActivity;

public class LifeCycleActivity extends LogActivity {
    private static final String TAG = LifeCycleActivity.class.getSimpleName();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_util);

        startNext();
    }

    void startNext(){
        startActivity(new Intent(this, LifeCycle_1_Activity.class));
    }
    
}
