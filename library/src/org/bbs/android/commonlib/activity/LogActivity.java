package org.bbs.android.commonlib.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * add log message to {@link Log}
 * @author bangbang.song@gmail.com
 *
 * @see #needLogLifecycle() needLogLifecycle and others.
 * @see #viewServerEnabled()
 */
public class LogActivity extends Activity {
	
	protected boolean needLogLifecycle() {
		return true;
	}
	protected boolean needLogSaveInstance() {
		return false;
	}
	protected boolean needLogMemory() {
		return false;
	}
	protected boolean needLogKey() {
		return false;
	}
	protected boolean needLogTouchEvent() {
		return false;
	}
	protected boolean needLogActivityResult() {
		return false;
	}
	/**
	 * for some device, hierarchy view do not work, in this case,
	 * you neeed this.
	 * @return
	 */
	protected boolean viewServerEnabled() {
        return false;
    }
    protected void log(String message) {
		Log.d(getClass().getSimpleName(), message);
	}
	
	// life cycle.
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (needLogLifecycle() || needLogSaveInstance()){
			log("onCreate(). savedInstanceState: " + savedInstanceState);
		}
		
		if (viewServerEnabled()) {
		    ViewServer.get(this).addWindow(this);
		}
	}
	@Override
	protected void onStart() {
		super.onStart();	
		
		if (needLogLifecycle()){
			log("onStart().");
		}
	}
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		
		if (needLogLifecycle()){
			log("onPostCreate(). savedInstanceState: " + savedInstanceState);
		}
	}
	@Override
	protected void onRestart() {
		super.onRestart();
		
		if (needLogLifecycle()){
			log("onRestart().");
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		
		if (needLogLifecycle()){
			log("onResume().");
		}
		
		if (viewServerEnabled()) {
		    ViewServer.get(this).setFocusedWindow(this);
		}
	}
	@Override
	protected void onPostResume() {
		super.onPostResume();
		
		if (needLogLifecycle()){
			log("onPostResume().");
		}
	}
	@Override
	protected void onPause() {
		super.onPause();
		
		if (needLogLifecycle()){
			log("onPause().");
		}
	}
	@Override
	protected void onStop() {
		super.onStop();
		
		if (needLogLifecycle()){
			log("onStop().");
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		if (needLogLifecycle()){
			log("onDestroy().");
		}
		
		if (viewServerEnabled()) {
		    ViewServer.get(this).removeWindow(this);
		}
	}
	
	// save & restore state.
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		if (needLogSaveInstance()) {
			log("onSaveInstanceState(). outState: " + outState);
		}
	}
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		
		if (needLogSaveInstance()) {
			log("onRestoreInstanceState(). savedInstanceState: " + savedInstanceState);
		}
	}
	@Override
	public Object onRetainNonConfigurationInstance() {	
		if (needLogSaveInstance()) {
			log("onRetainNonConfigurationInstance().");
		}
		return super.onRetainNonConfigurationInstance();
	}
	@Override
	@Deprecated
	public Object getLastNonConfigurationInstance() {
		Object state = super.getLastNonConfigurationInstance();
		if (needLogSaveInstance()) {
			log("getLastNonConfigurationInstance(). state: " + state);
		}
		
		return state;
	}
	
	// keyevent
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean handled = super.onKeyDown(keyCode, event);
		
		if (needLogKey()){
			log("onKeyDown(). handled: " + handled + " keyCode: " + keyCode + " event: " + event);
		}
		return handled;
	}
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		boolean handled =  super.onKeyUp(keyCode, event);
		
		if (needLogKey()){
			log("onKeyUp(). handled: " + handled + " keyCode: " + keyCode + " event: " + event);
		}
		return handled;
	}
	//lint @Override
	@SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.ECLAIR)
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
		boolean handled =  super.onKeyLongPress(keyCode, event);
		
		if (needLogKey()){
			log("onKeyLongPress(). handled: " + handled + " keyCode: " + keyCode + " event: " + event);
		}
		return handled;
	}
	@Override
	public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
		boolean handled =  super.onKeyMultiple(keyCode, repeatCount, event);
		
		if (needLogKey()){
			log("onKeyMultiple(). handled: " + handled + " keyCode: " + keyCode 
					+ " repeatCount: " + repeatCount + " event: " + event);
		}
		
		return handled;
	}
	//lint @Override
	@SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
		boolean handled =  super.onKeyShortcut(keyCode, event);
		
		if (needLogKey()){
			log("onKeyShortcut(). handled: " + handled + " keyCode: " + keyCode + " event: " + event);
		}
		return handled;
	}
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		boolean handled =  super.dispatchKeyEvent(event);
		
		if (needLogKey()){
			log("dispatchKeyEvent(). handled: " + handled + " event: " + event);
		}
		return handled;
	}
	//lint @Override
	@SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
		boolean handled =  super.dispatchKeyShortcutEvent(event);
		
		if (needLogKey()){
			log("dispatchKeyShortcutEvent(). handled: " + handled + " event: " + event);
		}
		return handled;
	}
	@Override
	public boolean dispatchTrackballEvent(MotionEvent event) {
		boolean handled =   super.dispatchTrackballEvent(event);		
		
		if (needLogKey()){
			log("dispatchTrackballEvent(). handled: " + handled + " event: " + event);
		}
		return handled;
	}
	@Override
	public boolean onTrackballEvent(MotionEvent event) {
		boolean handled =   super.onTrackballEvent(event);	
		
		if (needLogKey()){
			log("onTrackballEvent(). handled: " + handled + " event: " + event);
		}
		return handled;
	}
	
	// touch event
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean handled =  super.onTouchEvent(event);
		
		if (needLogTouchEvent()){
			log("onTouchEvent(). handled: " + handled + " event: " + event);
		}
		return handled;
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		boolean handled =  super.dispatchTouchEvent(event);
		
		if (needLogTouchEvent()){
			log("dispatchTouchEvent(). handled: " + handled + " event: " + event);
		}
		return handled;
	}
	//lint @Override
	@SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public boolean dispatchGenericMotionEvent(MotionEvent event) {
		boolean handled =  super.dispatchGenericMotionEvent(event);
		
		if (needLogTouchEvent()){
			log("dispatchGenericMotionEvent(). handled: " + handled + " event: " + event);
		}
		return handled;
	}
	
	// memory
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		
		if (needLogMemory()) {
			log("onLowMemory().");
		}
	}
	//lint @Override
	@SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void onTrimMemory(int level) {
		super.onTrimMemory(level);
		
		if (needLogMemory()) {
			log("onTrimMemory(). level: " + level);
		}
	}

	// activity result
	//lint @Override
	@SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void startActivityForResult(Intent intent, int requestCode,
			Bundle options) {
		super.startActivityForResult(intent, requestCode, options);
		
		if (needLogActivityResult()) {
			log("startActivityForResult(). intent: " + intent + " requestCode: " + requestCode
					+ " options: " + options);
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);	
		
		if (needLogActivityResult()) {
			log("onActivityResult(). requestCode: " + requestCode
					+ " resultCode: " + resultCode + " data: " + data);
		}
	}
}
