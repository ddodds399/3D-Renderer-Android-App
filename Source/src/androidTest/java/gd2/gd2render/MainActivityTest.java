package gd2.gd2render;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.widget.ListView;

import gd2.gd2render.Core.MainActivity;


public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    MainActivity activity;

    public MainActivityTest(){
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    @SmallTest
    public void testListViewNotNull(){
        ListView listView = (ListView) activity.findViewById(R.id.listView);
        assertNotNull(listView);
    }

    @UiThreadTest
    public void testOnBackPressed(){
        View original = activity.findViewById(android.R.id.content);
        activity.onBackPressed();
        assertSame(original, activity.findViewById(android.R.id.content));
    }


}
