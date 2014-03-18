package org.bangbang.song.android.commonlib;

import java.util.HashMap;
import java.util.Map;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

public class GLUtil {
    public static void logError(GL10 gl, String tag, String message){
        int error = gl.glGetError();
        int last = error;
        int i = 1;
        while (error != GL10.GL_NO_ERROR){
            last = error;
            error = gl.glGetError();
            if (error != GL10.GL_NO_ERROR) {
                Log.w(tag, "previous " + i + "th method has error. :" + glError(error));
            }
            i++;
        }
        
        Log.d(tag, message + " error: " + glError(last));
    }
    
    static Map<Integer, String> sErrorMap;
    static {
        sErrorMap = new HashMap<Integer, String>();

        sErrorMap.put(GL10.GL_NO_ERROR, "GL_NO_ERROR");
        sErrorMap.put(GL10.GL_INVALID_ENUM, "GL_INVALID_ENUM");
        sErrorMap.put(GL10.GL_INVALID_VALUE, "GL_INVALID_VALUE");
        sErrorMap.put(GL10.GL_INVALID_OPERATION, "GL_INVALID_OPERATION");
        sErrorMap.put(GL10.GL_STACK_OVERFLOW, "GL_STACK_OVERFLOW");
        sErrorMap.put(GL10.GL_STACK_UNDERFLOW, "GL_STACK_UNDERFLOW");
        sErrorMap.put(GL10.GL_OUT_OF_MEMORY, "GL_OUT_OF_MEMORY");
        sErrorMap.put(GL10.GL_NO_ERROR, "GL_NO_ERROR");
        sErrorMap.put(GL10.GL_NO_ERROR, "GL_NO_ERROR");
    }
    
    public static String glError(int error){
        String errorStr = "unknown error code. [" + error + "]";
        if (sErrorMap.containsKey(error)) {
            errorStr = sErrorMap.get(error);
        }
        
        return errorStr;
    }
}
