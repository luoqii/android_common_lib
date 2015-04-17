package org.bbs.android.commonlib;

import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;

public class ContextUtil {
	private static final String TAG = ContextUtil.class.getSimpleName();

	public static final void disableKeyguard(final Context context) {

		KeyguardLock keyguardLock = ((KeyguardManager) context
				.getSystemService(Context.KEYGUARD_SERVICE))
				.newKeyguardLock(TAG);

		keyguardLock.disableKeyguard();
	}
}
