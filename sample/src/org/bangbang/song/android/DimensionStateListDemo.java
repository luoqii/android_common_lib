package org.bangbang.song.android;

import java.io.IOException;

import org.bangbang.song.android.commonlib.ContextUtil;
import org.bangbang.song.android.commonlib.DimensionStateList;
import org.bangbang.song.android.commonlib.R;
import org.bangbang.song.android.commonlib.activity.LogActivity;
import org.bangbang.song.android.commonlib.demo.BuildConfig;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.Button;

public class DimensionStateListDemo extends LogActivity {
    private static final String TAG = DimensionStateListDemo.class.getSimpleName();
    // public for test only.
    public boolean mPaused;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_dimension_state_list_demo);
        
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
    
   public static class MyButton extends Button {


        private DimensionStateList mTextSize;

        public MyButton(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            // TODO Auto-generated constructor stub
        }

        public MyButton(Context context, AttributeSet attrs) {
            super(context, attrs);
            
            try {
                mTextSize = DimensionStateList.createFromXml(getResources(), 
                        getResources().getXml(R.xml.button_text_size));
            } catch (NotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            ;
        }

        public MyButton(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
        }
        
        @Override
        protected void drawableStateChanged() {
            super.drawableStateChanged();
            
            int size = mTextSize.getColorForState(getDrawableState(), 50);
            
            setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
        
    }
}
