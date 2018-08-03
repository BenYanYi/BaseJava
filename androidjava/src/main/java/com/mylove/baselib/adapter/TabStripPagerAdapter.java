package com.mylove.baselib.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author yanyi
 */

public class TabStripPagerAdapter extends FragmentPagerAdapter {
    private String[] titles;
    private List<Fragment> oList;

    public TabStripPagerAdapter(FragmentManager fm, String[] titles, List<Fragment> list) {
        super(fm);
        this.titles = titles;
        this.oList = list;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return oList.get(position);
    }

    @Override
    public int getCount() {
        return titles.length;
    }
}
