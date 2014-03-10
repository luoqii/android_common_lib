package org.bangbang.song.android.commonlib.test;

import org.bangbang.song.android.MainActivity;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivityTestCase extends ActivityInstrumentationTestCase2<MainActivity> {
	private MainActivity mAct;

	public MainActivityTestCase(){
		super(MainActivity.class);
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
	
	public void test_lable(){
		String expected = "demo";
		String actual = ((TextView)((ListView)getActivity().findViewById(android.R.id.list))
				.getChildAt(0)).getText().toString();
		
		assertEquals(expected, actual);
	}
}
