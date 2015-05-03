
package org.bbs.android.commonlib;

import org.bangbang.song.android.commonlib.BuildConfig;

import android.util.Log;

public class CrazyClicker {
    private static final String TAG = CrazyClicker.class.getSimpleName();
    private static final boolean DEBUG = BuildConfig.DEBUG && false;

    public static final int DEFAUTL_THRESHOLD = 300;
    public static final int DEFAUTL_HIT_LIMIT = 7;

    private Callback mCb;
    private int mHitLimit;
    private int mDiffThreshold;

    private int mHit;
    private long mLastClickTime = 0;
    
    public CrazyClicker(Callback cb) {
        this(DEFAUTL_HIT_LIMIT, DEFAUTL_THRESHOLD, cb);
    }

    public CrazyClicker(int hitLimit, int diffThreshold, Callback cb) {
        mHitLimit = hitLimit;
        mDiffThreshold = diffThreshold;
        mCb = cb;

        if (DEBUG) {
            Log.d(TAG, "litmit: " + hitLimit + " diff: " + diffThreshold);
        }
    }

    public void onClick() {
        if (mLastClickTime == 0) {
            mLastClickTime = System.currentTimeMillis();
            return;
        } else {
            long clickTime = System.currentTimeMillis();
            long diff = clickTime - mLastClickTime;

            if (DEBUG) {
                Log.d(TAG, "diff: " + diff + "\thit: " + mHit);
            }

            if (diff < mDiffThreshold) {
                mHit++;
            } else {
                mHit = 0;
                mLastClickTime = 0;
            }

            mLastClickTime = clickTime;

            if (mHit >= mHitLimit) {
                mLastClickTime = 0;
                mHit = 0;

                if (mCb != null) {
                    mCb.onFireInTheHole();
                }
            }
        }
    }

    public void setCallback(Callback cb) {
        mCb = cb;
    }

    static public interface Callback {
        public void onFireInTheHole();
    }
}
