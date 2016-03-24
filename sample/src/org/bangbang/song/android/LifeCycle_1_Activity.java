package org.bangbang.song.android;

import android.os.Bundle;

import org.bangbang.song.android.commonlib.demo.R;
import org.bbs.android.commonlib.activity.LogActivity;

public class LifeCycle_1_Activity extends LogActivity {
    private static final String TAG = LifeCycle_1_Activity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_util);
    }
    
}
