package com.modesteam.pardal.instrumentation;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.modesteam.pardal.BrandListFragment;
import com.modesteam.pardal.MainActivity;
import com.modesteam.pardal.R;

import models.Brand;
import models.City;
import models.HighwayStretch;
import models.Model;
import models.State;
import models.Type;

/**
 * Created by rafael on 05/05/15.
 */
public class TestMainActivity extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity mActivity;
    private Instrumentation mInstrumentation;

    public TestMainActivity(){
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        this.mActivity = getActivity();
        this.mInstrumentation = getInstrumentation();
    }

    public void openDrawerOptionAt(int position){
        Fragment fragment = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        DrawerLayout drawerLayout = (DrawerLayout)this.mActivity.findViewById(R.id.drawer_layout);
        if(!drawerLayout.isDrawerOpen(GravityCompat.START)){
            View v = fragment.getView().focusSearch(View.FOCUS_RIGHT);
            TouchUtils.clickView(this,v);
        }
        ListView listView = (ListView)fragment.getView();
        TouchUtils.clickView(this,listView.getChildAt(position));
    }
    public void testShouldSelectWelcome(){
        this.mActivity = getActivity();
        openDrawerOptionAt(0);
    }
    public void testShouldSelectShowRanking(){
        this.mActivity = getActivity();
        openDrawerOptionAt(1);
    }

    public Fragment openListFragment(int position){
        this.mActivity = getActivity();
        openDrawerOptionAt(1);
        Fragment fragment = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.container);
        mInstrumentation.waitForIdleSync();
        ImageButton imageButton = (ImageButton) (fragment.getView().findViewById(position));
        TouchUtils.clickView(this,imageButton);
        return  fragment;
    }


    public void testShouldShowStateList(){
        this.mActivity = getActivity();
        openListFragment(R.id.bState);
        openListFragment(R.id.bList);

    }
    public void testShouldShowCityList(){
        this.mActivity = getActivity();
        openListFragment(R.id.bCity);
        openListFragment(R.id.bList);
    }
    public void testShouldShowHighWayStretchList(){
        this.mActivity = getActivity();
        openListFragment(R.id.bHighway);
        openListFragment(R.id.bList);
    }

  public void testShouldShowModelList(){
        this.mActivity = getActivity();
        openListFragment(R.id.bModel);
        openListFragment(R.id.bList);
  }

    public void testShouldShowTypeList(){
        this.mActivity = getActivity();
        openListFragment(R.id.bType);
        openListFragment(R.id.bList);
    }
    public void testShouldShowBrandList(){
        this.mActivity = getActivity();
        openListFragment(R.id.bBrand);
        openListFragment(R.id.bList);
    }

    public Fragment openDetailFragment(int view){
        this.mActivity = getActivity();
        Fragment fragment3 = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.container);
        mInstrumentation.waitForIdleSync();
        ListView listView2 = (ListView) ((LinearLayout) fragment3.getView().findViewById(view)).getChildAt(1);
        TouchUtils.clickView(this,listView2.getChildAt(1));
        return fragment3;
    }

    public void testShouldShowStateDetail(){
        this.mActivity = getActivity();
        openListFragment(R.id.bState);
        openListFragment(R.id.bList);
        openDetailFragment(R.id.state_list_view);
    }
    public void testShouldShowCityDetail(){
        this.mActivity = getActivity();
        openListFragment(R.id.bCity);
        openListFragment(R.id.bList);
        openDetailFragment(R.id.city_list_view);
    }
    public void testShouldShowHighWayStretchDetail(){
        this.mActivity = getActivity();
        openListFragment(R.id.bHighway);
        openListFragment(R.id.bList);
        openDetailFragment(R.id.highway_list_view);
    }
    public void testShouldShowModelDetail(){
        this.mActivity = getActivity();
        openListFragment(R.id.bModel);
        openListFragment(R.id.bList);
        openDetailFragment(R.id.model_list_view);
    }
    public void testShouldShowTypeDetail(){
        this.mActivity = getActivity();
        openListFragment(R.id.bType);
        openListFragment(R.id.bList);
        openDetailFragment(R.id.type_list_view);
    }
    public void testShouldShowBrandDetail(){
        this.mActivity = getActivity();
        openListFragment(R.id.bBrand);
        openListFragment(R.id.bList);
        openDetailFragment(R.id. brand_list_view);
    }


    public void testShouldShowAbout(){
        this.mActivity = getActivity();
        openDrawerOptionAt(1);
        mInstrumentation.waitForIdleSync();
        Fragment fragment3 = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.container);
        ImageButton imageButton = (ImageButton) (fragment3.getView().findViewById(R.id.bAbout));
        TouchUtils.clickView(this,imageButton);

    }

//    public void testShouldFilterBrandList(){
//        this.mActivity = getActivity();
//        openListFragment(5);
//        Fragment fragment = mActivity.getSupportFragmentManager().findFragmentById(R.id.container);
//        EditText editText = (EditText) fragment.getView().findViewById(R.id.searchEditText);
//        ListView listView = (ListView) fragment.getView().findViewById(android.R.id.list);
//
//        TouchUtils.clickView(this,editText);
//        mInstrumentation.sendStringSync("asi");
//        assertEquals("ASIA",((Brand)listView.getAdapter().getItem(0)).getName());
//    }
//
//    public void testShouldFilterTypeList(){
//        this.mActivity = getActivity();
//        openListFragment(4);
//        Fragment fragment = mActivity.getSupportFragmentManager().findFragmentById(R.id.container);
//        EditText editText = (EditText) fragment.getView().findViewById(R.id.searchEditText);
//        ListView listView = (ListView) fragment.getView().findViewById(android.R.id.list);
//
//        TouchUtils.clickView(this,editText);
//        mInstrumentation.sendStringSync("car");
//        assertEquals("CARGA",((Type)listView.getAdapter().getItem(0)).getName());
//    }
//    public void testShouldFilterModelList(){
//        this.mActivity = getActivity();
//        openListFragment(3);
//        mInstrumentation.waitForIdleSync();
//        Fragment fragment = mActivity.getSupportFragmentManager().findFragmentById(R.id.container);
//        EditText editText = (EditText) fragment.getView().findViewById(R.id.searchEditText);
//        ListView listView = (ListView) fragment.getView().findViewById(android.R.id.list);
//        mInstrumentation.waitForIdleSync();
//        TouchUtils.clickView(this,editText);
//        mInstrumentation.waitForIdleSync();
//        mInstrumentation.sendStringSync("ford/fi");
//        mInstrumentation.waitForIdleSync();
//        assertEquals("FORD/FIESTA FLEX",((Model)listView.getAdapter().getItem(0)).getName());
//    }
//
//    public void testShouldFilterCityList(){
//        this.mActivity = getActivity();
//        openListFragment(1);
//        mInstrumentation.waitForIdleSync();
//        Fragment fragment = mActivity.getSupportFragmentManager().findFragmentById(R.id.container);
//        EditText editText = (EditText) fragment.getView().findViewById(R.id.searchEditText);
//        ListView listView = (ListView) fragment.getView().findViewById(android.R.id.list);
//        mInstrumentation.waitForIdleSync();
//        TouchUtils.clickView(this,editText);
//        mInstrumentation.waitForIdleSync();
//        mInstrumentation.sendStringSync("bu");
//        assertEquals("BUJARI",((City)listView.getAdapter().getItem(0)).getName());
//    }
//
//    public void testShouldFilterStateList(){
//        this.mActivity = getActivity();
//        openListFragment(0);
//        Fragment fragment = mActivity.getSupportFragmentManager().findFragmentById(R.id.container);
//        EditText editText = (EditText) fragment.getView().findViewById(R.id.searchEditText);
//        ListView listView = (ListView) fragment.getView().findViewById(android.R.id.list);
//
//        TouchUtils.clickView(this,editText);
//        mInstrumentation.sendStringSync("b");
//        mInstrumentation.waitForIdleSync();
//        assertEquals("BA",((State)listView.getAdapter().getItem(0)).getName());
//    }
//
//        public void testShouldFilterHighwayStretchList(){
//        this.mActivity = getActivity();
//        openListFragment(2);
//        mInstrumentation.waitForIdleSync();
//        Fragment fragment = mActivity.getSupportFragmentManager().findFragmentById(R.id.container);
//        EditText editText = (EditText) fragment.getView().findViewById(R.id.searchEditText);
//        ListView listView = (ListView) fragment.getView().findViewById(android.R.id.list);
//        mInstrumentation.waitForIdleSync();
//        TouchUtils.clickView(this,editText);
//        mInstrumentation.waitForIdleSync();
//        mInstrumentation.sendStringSync("222");
//        mInstrumentation.waitForIdleSync();
//        assertEquals("222",((HighwayStretch)listView.getAdapter().getItem(0)).getNumber());
//        assertEquals("4",((HighwayStretch)listView.getAdapter().getItem(0)).getKilometer());
//    }
}
