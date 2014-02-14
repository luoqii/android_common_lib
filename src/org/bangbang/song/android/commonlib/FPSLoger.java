package org.bangbang.song.android.commonlib;

import android.util.Log;

public class FPSLoger {
        private String mTag;
        private long mStartTime;
        private int mFramecount;

        public FPSLoger(String logTag) {
            mTag = logTag;
        }
        
        public void onDraw() {
            long tick = System.currentTimeMillis();
            mFramecount++;
            
            if (mStartTime == 0) {
                mStartTime = tick;
            } else {
                long elapseTime = tick - mStartTime;
                if (elapseTime > 1000) {
                    Log.d(mTag, "FPS: " + mFramecount);
                    
                    mStartTime = tick;
                    mFramecount = 0;
                }
            }
        }
}
