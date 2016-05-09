package org.bbs.android.commonlib;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Process;

import org.bbs.android.log.Log;

public class AndroidUtil {
	private static final String TAG = AndroidUtil.class.getSimpleName();


	public static void poweroff(){
		android.os.Process.killProcess(Process.myPid());
	}
	

	public static void restart(Context context){
		PackageManager pm = context.getPackageManager();
        //check if we got the PackageManager
        if (pm != null) {
            //create the intent with the default start activity for your application
            Intent mStartActivity = pm.getLaunchIntentForPackage(
                    context.getPackageName()
            );
            if (mStartActivity != null) {
                mStartActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //create a pending intent so the application is restarted after System.exit(0) was called. 
                // We use an AlarmManager to call this intent in 100ms
                int mPendingIntentId = 223344;
                PendingIntent mPendingIntent = PendingIntent
                        .getActivity(context, mPendingIntentId, mStartActivity,
                                PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                //kill the application
//                System.exit(0);
                poweroff();
            } else {
                Log.e(TAG, "Was not able to restart application, mStartActivity null");
            }
        }
	}
}
