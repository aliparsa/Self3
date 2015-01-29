package com.example.parsa.self3;



import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parsa.self3.DataModel.DateItem;
import com.example.parsa.self3.DataModel.MenuFood;
import com.example.parsa.self3.DataModel.Personnel;
import com.example.parsa.self3.DataModel.Shopping;
import com.example.parsa.self3.Helper.FontHelper;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.
    PagerAdapter mDemoCollectionPagerAdapter;
    ViewPager mViewPager;
    ActionBar actionBar;
    private ImageView user;
    private Context context;
    public Personnel personnel;
    public DateItem dateItem;
    private ImageView shopping;
    private ArrayList<MenuFood> selectedFoods;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        actionBar=getSupportActionBar();


        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        dateItem = (DateItem) getIntent().getSerializableExtra("dateItem");
        personnel = (Personnel) getIntent().getSerializableExtra("personnel");


        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {


            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }
        };



        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mDemoCollectionPagerAdapter =
                new PagerAdapter(
                        getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);


        actionBar.addTab(
                actionBar.newTab()
                        .setText("رزرو امروز")
                        .setTabListener(new ActionBar.TabListener() {

                            @Override
                            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                                mViewPager.setCurrentItem(tab.getPosition());

                            }

                            @Override
                            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

                            }

                            @Override
                            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

                            }
                        }));



        actionBar.addTab(
                actionBar.newTab()
                        .setText("منو غذا")
                        .setTabListener(new ActionBar.TabListener() {
                            @Override
                            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                                mViewPager.setCurrentItem(tab.getPosition());

                            }

                            @Override
                            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

                            }

                            @Override
                            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

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



        prepareActionBar();
    }

    private void prepareActionBar() {

        View customActionBar = getLayoutInflater().inflate(R.layout.actionbar_view, null);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setCustomView(customActionBar);

        actionBar.setLogo(null); // forgot why this one but it helped
        actionBar.setIcon(null);

        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(false);

        View homeIcon = findViewById(android.R.id.home);

        if (homeIcon != null) {
            homeIcon.setVisibility(View.GONE);
        }
        if (homeIcon.getParent() != null) {
            ((View) homeIcon.getParent()).setVisibility(View.GONE);
        }


        TextView title = (TextView) customActionBar.findViewById(R.id.ac_title);
        FontHelper.SetFontBold(this, FontHelper.Fonts.MAIN_FONT, title);

        title.setText("انتخاب تاریخ");
        //ImageView back = (ImageView) customActionBar.findViewById(R.id.ac_back);
        LinearLayout back = (LinearLayout) customActionBar.findViewById(R.id.ac_back_layout);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        user = (ImageView) customActionBar.findViewById(R.id.ac_action1);
        shopping = (ImageView) customActionBar.findViewById(R.id.ac_action2);

        user.setImageResource(R.drawable.ic_user);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(context,PersonnelInfoActivity.class);
                intent.putExtra("personnel",personnel);
                startActivity(intent);

            }
        });

        shopping.setImageResource(R.drawable.ic_shopping_cart);
        shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mViewPager.setCurrentItem(0);
                Intent intent = new Intent(context,BasketActivity.class);
                intent.putExtra("personnel",personnel);
                intent.putExtra("dateItem",dateItem);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(Shopping.selectedFoods.size() > 0){
            shopping.setImageResource(R.drawable.ic_shopping_cart_i);
        }else{
            shopping.setImageResource(R.drawable.ic_shopping_cart);
        }
    }

    public void addNewFood(MenuFood menuFood){
        shopping.setImageResource(R.drawable.ic_shopping_cart_i);
    }
}

