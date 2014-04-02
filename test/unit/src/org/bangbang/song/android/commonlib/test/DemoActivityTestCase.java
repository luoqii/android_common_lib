package org.bangbang.song.android.commonlib.test;

import org.bangbang.song.android.DemoCrazyClicker;
import org.bangbang.song.android.commonlib.R;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;

public class DemoActivityTestCase extends ActivityInstrumentationTestCase2<Demo> {
	private Demo mAct;

	public DemoActivityTestCase(){
		super(Demo.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		
		 mAct = getActivity();
	}
	
	@SmallTest
	public void testPreconditions() {
		assertNotNull("activity is null", mAct);
	}
	
	public void test_contentIsVisiable(){
		final View decorView = mAct.getWindow().getDecorView();
		View contentView = mAct.findViewById(R.id.content);
		ViewAsserts.assertOnScreen(decorView, contentView);
		assertTrue(View.VISIBLE == contentView.getVisibility());
	}
}
