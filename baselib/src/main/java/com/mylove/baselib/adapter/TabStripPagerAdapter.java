package com.mylove.baselib.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author myLove
 * @time 2018-01-15 15:29
 * @e-mail love@yanyi.red
 * @overview
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
