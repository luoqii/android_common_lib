package org.bangbang.song.android.commonlib.uiautomator;

import android.os.RemoteException;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class BaseUiAutomatorTestCase extends UiAutomatorTestCase {
    public static final String A_BUTTON = "android.widget.Button";
    public static final String A_IMAGE_BUTTON = "android.widget.ImageButton";
    public static final String A_RELATIVE_LAYOUT = "android.widget.RelativeLayout";
    public static final String A_TEXTVIEW = "android.widget.TextView";
    private UiDevice mDevice;
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        mDevice = UiDevice.getInstance();
    }
    
    public void wakeUp() throws RemoteException {
        mDevice.wakeUp();
    }
    
    public void pressBack() {
        mDevice.pressBack();
    }
    
    public void pressDPadLeft() {
        mDevice.pressDPadLeft();
    }
    
    public void pressDPadRight() {
        mDevice.pressDPadRight();
    }
    
    public void pressDPadUp() {
        mDevice.pressDPadUp();
    }
    
    public void pressDPadDown() {
        mDevice.pressDPadDown();
    }
    
    public void pressDPadDown(int limit) {
        for (int i = 0 ; i < limit; i++) {
            mDevice.pressDPadDown();
        }
    }
    
    public void pressDPadUp(int limit) {
        for (int i = 0 ; i < limit; i++) {
            mDevice.pressDPadUp();
        }
    }
    
    public void pressDPadLeft(int limit) {
        for (int i = 0 ; i < limit; i++) {
            mDevice.pressDPadLeft();
        }
    }
    
    public void pressDPadRight(int limit) {
        for (int i = 0 ; i < limit; i++) {
            mDevice.pressDPadRight();
        }
    }
    
    public void pressDPadCenter(int limit) {
        for (int i = 0 ; i < limit; i++) {
            mDevice.pressDPadCenter();
        }
    }
    
    public void pressDPadCenter() {
        mDevice.pressDPadCenter();
    }
    
    public void waitForIdle() {
        mDevice.waitForIdle();
    }

    protected void assertUiExist(UiObject ui) {
        assertTrue("no such ui: " + ui.getSelector(), ui.exists());
    }
    
    protected void assertUiNotExist(UiObject ui) {
        assertTrue("have ui: " + ui.getSelector(), !ui.exists());
    }

    protected void logD(String tag, String string) {
        System.out.println(tag + ": " + string); 
    }  
    protected void logE(String tag, String string) {
        System.err.println(tag + ": " + string); 
    }
    
}
