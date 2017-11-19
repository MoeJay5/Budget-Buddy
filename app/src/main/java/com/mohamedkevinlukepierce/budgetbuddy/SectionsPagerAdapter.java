package com.mohamedkevinlukepierce.budgetbuddy;

/**
 * Created by kbphan on 11/19/17.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> FRAGMENT_LIST = new ArrayList<>();
    private final List<String> FRAGMENT_TITLE_LIST = new ArrayList<>();

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment
        return FRAGMENT_LIST.get(position);
    }

    @Override
    public int getCount() {
        // Show number of pages
        return FRAGMENT_LIST.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return FRAGMENT_TITLE_LIST.get(position);
    }
}