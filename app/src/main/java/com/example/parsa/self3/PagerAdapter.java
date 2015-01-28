package com.example.parsa.self3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.parsa.self3.Fragment.PageOneFragment;
import com.example.parsa.self3.Fragment.MenuFoodFragment;

// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
public class PagerAdapter extends FragmentStatePagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment;
        Bundle args;
        try {
            switch (i) {
                case 0:
                    fragment = new PageOneFragment();
                    args = new Bundle();
                    // Our object is just an integer :-P
                    args.putInt(PageOneFragment.ARG_OBJECT, i + 1);
                    fragment.setArguments(args);
                    return fragment;


                case 1:

                    fragment = new MenuFoodFragment();
                    args = new Bundle();
                    // Our object is just an integer :-P
                    args.putInt(MenuFoodFragment.ARG_OBJECT, i + 1);
                    fragment.setArguments(args);
                    return fragment;
            }

            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }
}
