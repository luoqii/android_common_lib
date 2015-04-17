package org.bbs.android.commonlib;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.TextView;

public class ViewUtil {

    private static final String TAG = ViewUtil.class.getSimpleName();

    public static void offsetRectBetween(View source, View dest, Rect rectInSource) {
        View root = source.getRootView();
        if (root instanceof ViewGroup) {
            ((ViewGroup) root).offsetDescendantRectToMyCoords(source, rectInSource);
            ((ViewGroup) root).offsetRectIntoDescendantCoords(dest, rectInSource);
        } else {
            Log.e(TAG, "view's parent is NOT a ViewGroup.");
        }
    }
    
    public static Bitmap getBitmap(View focus, int newWidth, int newHeight) {
        int oldWidth = focus.getWidth();
        int oldHeight = focus.getHeight();
        int oldLeft = focus.getLeft();
        int oldTop = focus.getTop();
        int oldWSpec = ReflectUtil.getIntFieldValue(View.class, focus, "mOldWidthMeasureSpec");
        int oldHSpec = ReflectUtil.getIntFieldValue(View.class, focus, "mOldHeightMeasureSpec");
        
        int w = newWidth;
        int h = newHeight;
        if (w == 0 || h == 0) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        int newWSpec = MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY);
        int newHSpec = MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY);
        focus.measure(newWSpec, newHSpec);
        focus.layout(0, 0, w, h);
        focus.draw(new Canvas(bitmap));
        
        // restore previous state.
        focus.measure(oldWSpec, oldHSpec);
        focus.layout(oldLeft, oldTop, oldLeft + oldWidth, oldTop + oldHeight);
        
//        Log.d(TAG, "bitmap w: " + bitmap.getWidth() + " h: " + bitmap.getHeight());
        return bitmap;
    }
    
    /**
     * @param group
     * @param searched
     * @param flags
     * @return
     * @see {@link android.view.View#FIND_VIEWS_WITH_TEXT}
     * @see {@link android.view.View#FIND_VIEWS_WITH_CONTENT_DESCRIPTION}
     * @see {@link android.view.View#findViewsWithText(ArrayList, CharSequence, int)}
     */
    public static void findViewsWithText(ViewGroup group, List<View> views, CharSequence searched, int flags) {
        if (TextUtils.isEmpty(searched)) {
            return;
        }
        
        if ((flags & View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION) != 0) {
            CharSequence contentDescription = group.getContentDescription();
            if (searched.equals(contentDescription)) {
                views.add(group);
            }
        }
        
        int childCount = group.getChildCount();
        for (int i = 0 ; i < childCount; i++) {
            View child = group.getChildAt(i);
            if (child instanceof ViewGroup) {
                findViewsWithText((ViewGroup)child, views, searched, flags);
            } else {
                findViewsWithText(child, views, searched, flags);
            }
        }
    }
    
    private static void findViewsWithText(View view, List<View> views, CharSequence searched, int flags) {
        if (TextUtils.isEmpty(searched)) {
            return;
        }
        
        if ((flags & View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION) != 0) {
            CharSequence contentDescription = view.getContentDescription();
            if (searched == contentDescription) {
                views.add(view);
            }
        } else if ((flags & View.FIND_VIEWS_WITH_TEXT) != 0 && view instanceof TextView){
            CharSequence text = ((TextView)view).getText();
            if (searched.equals(text)) {
                views.add(view);
            }
        }
    }
  
}
