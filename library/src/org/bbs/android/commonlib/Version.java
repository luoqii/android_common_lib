package org.bbs.android.commonlib;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class Version {
	private static final int INVALID_CODE = -1;
	private static final String PREF_NAME = Version.class.getName() + ".pref";
	private static final String KEY_PREVIOUS_V_CODE = "previous_version_code";
	private static final String KEY_PREVIOUS_V_NAME = "previous_version_name";
	private static final String TAG = Version.class.getSimpleName();
	private static Version sInstance;
	
	public static Version getInstance(){
		if (null == sInstance){
			sInstance = new Version();
		}
		
		return sInstance;
	}

	private int mCurrentVersionCode;
	private String mCurrentVersionName;
	private int mPreviousVersionCode;
	private String mPreviousVersionName;
	
	private Version(){};
	
	public void init(Application appContext){
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
	}
		
	public int getVersionCode(){
		return mCurrentVersionCode;
	}
	
	public String getVersionName() {
		return mCurrentVersionName;
	}	
	
	public int getPreviousVersionCode(){
		return mPreviousVersionCode;
	}
	
	public String getPreviousVersionName() {
		return mPreviousVersionName;
	}
	
	public boolean firstUsage(){
		return mPreviousVersionCode == INVALID_CODE;
	}
	
	public boolean appUpdated(){
		return mPreviousVersionCode != mCurrentVersionCode;
	}
}
