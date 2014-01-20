
package org.bangbang.song.android.commonlib;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.util.Log;
import android.view.Window;

public class WindowReflectUtil {
    private static final String TAG = WindowReflectUtil.class.getSimpleName();

    private WindowReflectUtil() {}// Utility class, do not instantiate.

    public static Window makeNewWindow(Context context) {
        Window w = null;
        try {
            Class<?> clazz;
            clazz = Class.forName("com.android.internal.policy.PolicyManager");
            Method method = clazz.getMethod("makeNewWindow", Context.class);
            w = (Window) method.invoke(clazz, context);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "ClassNotFoundException", e);// ignore this, it's safe.
        } catch (SecurityException e) {
            Log.e(TAG, "SecurityException", e);// ignore this, it's safe.
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "NoSuchMethodException", e);// ignore this, it's safe.
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "IllegalArgumentException", e);// ignore this, it's safe.
        } catch (IllegalAccessException e) {
            Log.e(TAG, "IllegalAccessException", e);// ignore this, it's safe.
        } catch (InvocationTargetException e) {
            Log.e(TAG, "InvocationTargetException", e);// ignore this, it's
                                                       // safe.
        }

        return w;
    }
}
