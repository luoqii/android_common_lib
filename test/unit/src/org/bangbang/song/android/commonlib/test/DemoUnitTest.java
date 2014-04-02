package org.bangbang.song.android.commonlib.test;

import org.bangbang.song.android.DemoCrazyClicker;

import android.content.Intent;
import android.test.ActivityUnitTestCase;

public class DemoUnitTest extends ActivityUnitTestCase<Demo> {
	public DemoUnitTest(){
		super(Demo.class);
	}
	
	public void test_lifecycle(){
		Intent intent = new Intent(getInstrumentation().getTargetContext(), Demo.class);
		Demo a = startActivity(intent, null, null);
		
		assertNotNull(a);
		getInstrumentation().callActivityOnResume(a);
		assertFalse(a.mPaused);
		getInstrumentation().callActivityOnPause(a);
		assertTrue(a.mPaused);
	}
}
