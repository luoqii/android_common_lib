package org.bbs.android.commonlib.activity;

// do not edit this file, copied from LogActivity.java
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

// do not edit this file, copied from LogActivity.java
/**
 * add log message to {@link Log}
 * @author bangbang.song@gmail.com
 *
 * @see #needLogLifecycle() needLogLifecycle and others.
 * @see #viewServerEnabled()
 */
public class Log_ListActivity extends ListActivity {
	
	protected boolean needLogLifecycle() {
// do not edit this file, copied from LogActivity.java
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
// do not edit this file, copied from LogActivity.java
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
// do not edit this file, copied from LogActivity.java
	 * @return
	 */
	protected boolean viewServerEnabled() {
        return false;
    }
    protected void log(String message) {
		Log.d(getClass().getSimpleName(), message);
	}
	
	// life cycle.
// do not edit this file, copied from LogActivity.java
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (needLogLifecycle() || needLogSaveInstance()){
			log("onCreate(). savedInstanceState: " + savedInstanceState);
		}
		
		if (viewServerEnabled()) {
		    ViewServer.get(this).addWindow(this);
// do not edit this file, copied from LogActivity.java
		}
	}
	@Override
	protected void onStart() {
		super.onStart();	
		
		if (needLogLifecycle()){
			log("onStart().");
		}
	}
// do not edit this file, copied from LogActivity.java
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		
		if (needLogLifecycle()){
			log("onPostCreate(). savedInstanceState: " + savedInstanceState);
		}
	}
	@Override
	protected void onRestart() {
// do not edit this file, copied from LogActivity.java
		super.onRestart();
		
		if (needLogLifecycle()){
			log("onRestart().");
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		
// do not edit this file, copied from LogActivity.java
		if (needLogLifecycle()){
			log("onResume().");
		}
		
		if (viewServerEnabled()) {
		    ViewServer.get(this).setFocusedWindow(this);
		}
	}
	@Override
	protected void onPostResume() {
// do not edit this file, copied from LogActivity.java
		super.onPostResume();
		
		if (needLogLifecycle()){
			log("onPostResume().");
		}
	}
	@Override
	protected void onPause() {
		super.onPause();
		
// do not edit this file, copied from LogActivity.java
		if (needLogLifecycle()){
			log("onPause().");
		}
	}
	@Override
	protected void onStop() {
		super.onStop();
		
		if (needLogLifecycle()){
			log("onStop().");
// do not edit this file, copied from LogActivity.java
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		if (needLogLifecycle()){
			log("onDestroy().");
		}
		
// do not edit this file, copied from LogActivity.java
		if (viewServerEnabled()) {
		    ViewServer.get(this).removeWindow(this);
		}
	}
	
	// save & restore state.
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
// do not edit this file, copied from LogActivity.java
		if (needLogSaveInstance()) {
			log("onSaveInstanceState(). outState: " + outState);
		}
	}
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		
		if (needLogSaveInstance()) {
			log("onRestoreInstanceState(). savedInstanceState: " + savedInstanceState);
// do not edit this file, copied from LogActivity.java
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
// do not edit this file, copied from LogActivity.java
	@Deprecated
	public Object getLastNonConfigurationInstance() {
		Object state = super.getLastNonConfigurationInstance();
		if (needLogSaveInstance()) {
			log("getLastNonConfigurationInstance(). state: " + state);
		}
		
		return state;
	}
	
// do not edit this file, copied from LogActivity.java
	// keyevent
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean handled = super.onKeyDown(keyCode, event);
		
		if (needLogKey()){
			log("onKeyDown(). handled: " + handled + " keyCode: " + keyCode + " event: " + event);
		}
		return handled;
	}
// do not edit this file, copied from LogActivity.java
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		boolean handled =  super.onKeyUp(keyCode, event);
		
		if (needLogKey()){
			log("onKeyUp(). handled: " + handled + " keyCode: " + keyCode + " event: " + event);
		}
		return handled;
	}
	//lint @Override
// do not edit this file, copied from LogActivity.java
	@SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.ECLAIR)
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
		boolean handled =  super.onKeyLongPress(keyCode, event);
		
		if (needLogKey()){
			log("onKeyLongPress(). handled: " + handled + " keyCode: " + keyCode + " event: " + event);
		}
		return handled;
	}
// do not edit this file, copied from LogActivity.java
	@Override
	public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
		boolean handled =  super.onKeyMultiple(keyCode, repeatCount, event);
		
		if (needLogKey()){
			log("onKeyMultiple(). handled: " + handled + " keyCode: " + keyCode 
					+ " repeatCount: " + repeatCount + " event: " + event);
		}
		
		return handled;
// do not edit this file, copied from LogActivity.java
	}
	//lint @Override
	@SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
		boolean handled =  super.onKeyShortcut(keyCode, event);
		
		if (needLogKey()){
			log("onKeyShortcut(). handled: " + handled + " keyCode: " + keyCode + " event: " + event);
		}
// do not edit this file, copied from LogActivity.java
		return handled;
	}
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		boolean handled =  super.dispatchKeyEvent(event);
		
		if (needLogKey()){
			log("dispatchKeyEvent(). handled: " + handled + " event: " + event);
		}
		return handled;
// do not edit this file, copied from LogActivity.java
	}
	//lint @Override
	@SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
		boolean handled =  super.dispatchKeyShortcutEvent(event);
		
		if (needLogKey()){
			log("dispatchKeyShortcutEvent(). handled: " + handled + " event: " + event);
		}
// do not edit this file, copied from LogActivity.java
		return handled;
	}
	@Override
	public boolean dispatchTrackballEvent(MotionEvent event) {
		boolean handled =   super.dispatchTrackballEvent(event);		
		
		if (needLogKey()){
			log("dispatchTrackballEvent(). handled: " + handled + " event: " + event);
		}
		return handled;
// do not edit this file, copied from LogActivity.java
	}
	@Override
	public boolean onTrackballEvent(MotionEvent event) {
		boolean handled =   super.onTrackballEvent(event);	
		
		if (needLogKey()){
			log("onTrackballEvent(). handled: " + handled + " event: " + event);
		}
		return handled;
	}
// do not edit this file, copied from LogActivity.java
	
	// touch event
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean handled =  super.onTouchEvent(event);
		
		if (needLogTouchEvent()){
			log("onTouchEvent(). handled: " + handled + " event: " + event);
		}
		return handled;
// do not edit this file, copied from LogActivity.java
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		boolean handled =  super.dispatchTouchEvent(event);
		
		if (needLogTouchEvent()){
			log("dispatchTouchEvent(). handled: " + handled + " event: " + event);
		}
		return handled;
	}
// do not edit this file, copied from LogActivity.java
	//lint @Override
	@SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public boolean dispatchGenericMotionEvent(MotionEvent event) {
		boolean handled =  super.dispatchGenericMotionEvent(event);
		
		if (needLogTouchEvent()){
			log("dispatchGenericMotionEvent(). handled: " + handled + " event: " + event);
		}
		return handled;
// do not edit this file, copied from LogActivity.java
	}
	
	// memory
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		
		if (needLogMemory()) {
			log("onLowMemory().");
		}
// do not edit this file, copied from LogActivity.java
	}
	//lint @Override
	@SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void onTrimMemory(int level) {
		super.onTrimMemory(level);
		
		if (needLogMemory()) {
			log("onTrimMemory(). level: " + level);
		}
// do not edit this file, copied from LogActivity.java
	}

	// activity result
	//lint @Override
	@SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void startActivityForResult(Intent intent, int requestCode,
			Bundle options) {
		super.startActivityForResult(intent, requestCode, options);
		
// do not edit this file, copied from LogActivity.java
		if (needLogActivityResult()) {
			log("startActivityForResult(). intent: " + intent + " requestCode: " + requestCode
					+ " options: " + options);
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);	
		
		if (needLogActivityResult()) {
// do not edit this file, copied from LogActivity.java
			log("onActivityResult(). requestCode: " + requestCode
					+ " resultCode: " + resultCode + " data: " + data);
		}
	}
}
