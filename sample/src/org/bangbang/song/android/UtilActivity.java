package org.bangbang.song.android;

import org.bangbang.song.android.commonlib.demo.R;
import org.bbs.android.commonlib.AndroidUtil;
import org.bbs.android.commonlib.activity.LogActivity;
import org.bbs.android.log.Logcat_FragmentActivity;

import android.os.Bundle;
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
    
}
