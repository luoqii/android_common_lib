package org.bbs.android.commonlib;

import android.content.Context;
import android.widget.Toast;

public class ActivityUtil {
	private static final String TAG = ActivityUtil.class.getSimpleName();

	public static final void toast(Context context, String text, int duration) {
		Toast.makeText(context, text, duration).show();
	}
}
