package org.bbs.android.commonlib;

import android.util.Log;

public class FPSLoger {
        private String mTag;
        private long mStartTime;
        private int mFramecount;
        private long mFrameStart;
        private long mFrameEnd;

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

        public void onDrawStart() {
            mFrameStart = System.currentTimeMillis();
        }
        public void onDrawEnd() {
            mFrameEnd = System.currentTimeMillis();
            
            long elapse = mFrameEnd - mFrameStart;
            Log.d(mTag, "frame #" + elapse);
        }
}
