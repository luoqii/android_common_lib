package org.bbs.android.commonlib;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

public class LogRender implements Renderer {

    private static final boolean DEBUG = true;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        if (DEBUG) {
            Log.d(getTag(), "onSurfaceCreated. gl: " + gl + " config: " + config);
        }
    }

    private String getTag() {
        return getClass().getSimpleName();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int w, int h) {
        if (DEBUG) {
            Log.d(getTag(), "onSurfaceChanged. gl: " + gl + " w: " + w + " h: " + h);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        if (DEBUG) {
            Log.d(getTag(), "onDrawFrame. gl: " + gl);
        }
    }

}
