package org.bangbang.song.android.commonlib.uiautomator;

import android.os.RemoteException;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class DemoUiTest extends UiAutomatorTestCase {
	public void testDemo() throws UiObjectNotFoundException, RemoteException {
		UiDevice.getInstance().wakeUp();
		
		UiObject firstText = new UiObject(
				new UiSelector().className("android.widget.TextView")
//				.childSelector(new UiSelector().className("android.widget.ListView"))
				.fromParent(new UiSelector().className("android.widget.ListView"))
				);
		
		UiObject o = new UiObject(new UiSelector()
		.className("android.widget.ListView")
		.childSelector(new UiSelector().className("android.widget.TextView")));
		o.getText();
				String expected = "demo";
		String actual = o.getText();
		assertEquals(expected, actual);
		
		o.click();
		getUiDevice().waitForIdle();
		
		o = new UiObject(new UiSelector().className("android.widget.RelativeLayout")
				.childSelector(new UiSelector().className("android.widget.TextView"))
				);
		assertNotNull(o.getText());
	}
}
