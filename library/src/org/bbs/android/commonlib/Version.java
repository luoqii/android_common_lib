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
	private static Map<Reference<Application>, Version>  sInstances = new HashMap<Reference<Application>, Version>();
	
	public static Version getInstance(Application appContext){
		Version v = null;
		for (Reference<Application> r : sInstances.keySet()) {
			if (r != null && r.get() == appContext) {
				v = sInstances.get(r);
				if (null != v){
					return v;
				}
			}
		}
		if (null == v){
			v = new Version(appContext);
			sInstances.put(new WeakReference<Application>(appContext), v);
		}
		
		return v;
	}

	private int mCurrentVersionCode;
	private String mCurrentVersionName;
	private int mPreviousVersionCode;
	private String mPreviousVersionName;
	private boolean mInited;
	
	private Version(Application appContext){
		PREF_NAME = appContext.getPackageName() + "." + PREF_NAME;
		init(appContext);
	};
	
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