package org.bbs.android.commonlib;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

public class Version {
	private static final int INVALID_CODE = -1;
	private /*static*/ /*final*/ String PREF_NAME = Version.class.getSimpleName() + "";
	private static final String KEY_PREVIOUS_V_CODE = "previous_version_code";
	private static final String KEY_PREVIOUS_V_NAME = "previous_version_name";
	private static final String TAG = Version.class.getSimpleName();

	private static Version  INSTANCE = null;

	private int mCurrentVersionCode;
	private String mCurrentVersionName;
	private int mPreviousVersionCode;
	private String mPreviousVersionName;
	private boolean mInited;
	
	public static Version getInstance(Application appContext){
		if (null == INSTANCE) {
			synchronized (Version.class) {
				if (null == INSTANCE) {
					INSTANCE = new Version(appContext);
				}
			}
		}
		
		return INSTANCE;
	}
	
	private Version(Application appContext){
		PREF_NAME = appContext.getPackageName() + "." + PREF_NAME;
		init(appContext);
	}

	void assertHasInit() {
		if (!mInited) {
			throw new IllegalStateException("you must init this with init(Application) method.");
		}
	}
	
	void init(Application appContext){
		if (mInited) {
			Log.w(TAG, "this has inited already, ignore.");
			return;
		}
		try {
			PackageInfo pInfo = appContext.getPackageManager().getPackageInfo(appContext.getPackageName(), 0);
			mCurrentVersionCode = pInfo.versionCode;
			mCurrentVersionName = pInfo.versionName;
			
			SharedPreferences p = appContext.getSharedPreferences(PREF_NAME, 0);
			mPreviousVersionCode = p.getInt(KEY_PREVIOUS_V_CODE, INVALID_CODE);
			mPreviousVersionName = p.getString(KEY_PREVIOUS_V_NAME, "");

			Log.i(TAG, "mCurrentVersionCode  : " + mCurrentVersionCode);
			Log.i(TAG, "mCurrentVersionName  : " + mCurrentVersionName);
			Log.i(TAG, "mPreviousVersionCode : " + mPreviousVersionCode);
			Log.i(TAG, "mPreviousVersionName : " + mPreviousVersionName);
			
			p.edit()
				.putInt(KEY_PREVIOUS_V_CODE, mCurrentVersionCode)
				.putString(KEY_PREVIOUS_V_NAME, mCurrentVersionName)
				.commit();
		} catch (NameNotFoundException e) {
			throw new RuntimeException("can not get packageinfo. ");
		}
		mInited = true;
	}
		
	public int getVersionCode(){
		assertHasInit();
		return mCurrentVersionCode;
	}
	
	public String getVersionName() {
		assertHasInit();
		return mCurrentVersionName;
	}	
	
	public int getPreviousVersionCode(){
		assertHasInit();
		return mPreviousVersionCode;
	}
	
	public String getPreviousVersionName() {
		assertHasInit();
		return mPreviousVersionName;
	}
	
	public boolean firstUsage(){
		assertHasInit();
		return mPreviousVersionCode == INVALID_CODE;
	}
	
	public boolean appUpdated(){
		assertHasInit();
		return mPreviousVersionCode != mCurrentVersionCode;
	}
}