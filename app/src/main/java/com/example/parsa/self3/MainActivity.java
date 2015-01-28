package com.example.parsa.self3;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;


public class MainActivity extends FragmentActivity {
    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.
    PagerAdapter mDemoCollectionPagerAdapter;
    ViewPager mViewPager;
    ActionBar actionBar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // init
        mViewPager =  (ViewPager) findViewById(R.id.pager);



        actionBar=getActionBar();


        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                // show the given tab
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // hide the given tab
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // probably ignore this event
            }

            @Override
            public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {

            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {

            }
        };






        actionBar.addTab(
                actionBar.newTab()
                        .setText("رزرو امروز")
                        .setTabListener(new ActionBar.TabListener() {
                            @Override
                            public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {
                                mViewPager.setCurrentItem(tab.getPosition());
                            }

                            @Override
                            public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {

                            }

                            @Override
                            public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {

                            }
                        }));



        actionBar.addTab(
                actionBar.newTab()
                        .setText("منو غذا")
                        .setTabListener(new ActionBar.TabListener() {
                            @Override
                            public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {
                                mViewPager.setCurrentItem(tab.getPosition());
                            }

                            @Override
                            public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {

                            }

                            @Override
                            public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {

                            }
                        }));



        // تغییر تب فعال هنگام سویپ روی صفحه
        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        actionBar.setSelectedNavigationItem(position);
                    }
                }
        );





        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mDemoCollectionPagerAdapter =
                new PagerAdapter(
                        getSupportFragmentManager());


        mViewPager.setAdapter(mDemoCollectionPagerAdapter);

        mViewPager.setCurrentItem(1);


        // Lets Make History

    }
}

