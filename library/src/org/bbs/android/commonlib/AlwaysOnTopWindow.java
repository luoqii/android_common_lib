package org.bbs.android.commonlib;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public class AlwaysOnTopWindow {
    private static final String TAG = AlwaysOnTopWindow.class.getSimpleName();

    protected final Context mContext;
    private View mContentView;
    private int mContentRes;
    private WindowManager mWindowManager;

	private LayoutParams mParam;

    public AlwaysOnTopWindow(Context context){
        mContext = context;
    }

    public void setContentView(View content){
        mContentView = content;
    }
    public void setContentView(int layoutResId){
        mContentRes = layoutResId;
    }

    public View getContentView(){
        return onCreateContentView();
    }

    public void show(){
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

        View content = onCreateContentView();
        WindowManager.LayoutParams p = onCreateLayoutParams();
        mWindowManager.addView(content, p);
    }

    public void dismiss(){
        mWindowManager.removeViewImmediate(mContentView);
    }
    
    public void updateLayoutParame(){
    	mWindowManager.updateViewLayout(getContentView(), mParam);
    }

    protected WindowManager.LayoutParams onCreateLayoutParams() {
        //http://stackoverflow.com/questions/4481226/creating-a-system-overlay-window-always-on-top
        mParam = new WindowManager.LayoutParams();
        mParam.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        mParam.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mParam.setTitle(TAG);
        return mParam;
    }

    private View onCreateContentView() {
        if (mContentView == null) {
            mContentView =  View.inflate(mContext, mContentRes, null);
        }

        return mContentView;
    }
}

