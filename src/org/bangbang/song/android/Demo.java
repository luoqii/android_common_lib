package org.bangbang.song.android;

import org.bangbang.song.android.commonlib.R;

import android.app.Activity;
import android.os.Bundle;

public class Demo extends Activity {
    private static final String TAG = Demo.class.getSimpleName();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
    }
}
