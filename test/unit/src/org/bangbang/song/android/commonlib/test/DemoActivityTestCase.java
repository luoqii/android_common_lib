package org.bangbang.song.android.commonlib.test;

import org.bangbang.song.android.Demo;
import org.bangbang.song.android.MainActivity;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;
import android.widget.TextView;

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
	
	public void testPreconditions() {
		assertNotNull("activity is null", mAct);
	}
}
