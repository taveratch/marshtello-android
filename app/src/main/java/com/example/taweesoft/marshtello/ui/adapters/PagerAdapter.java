package com.example.taweesoft.marshtello.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.taweesoft.marshtello.utils.DataCenter;

import java.util.Observer;

/**
 * Pager adapter use in MainActivity to show all cardlist.
 * Created by TAWEESOFT on 2/27/16 AD.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    private int numberOftabs;

    /**
     * Constructor.
     * @param fm
     * @param numberOftabs
     */
    public PagerAdapter(FragmentManager fm , int numberOftabs) {
        super(fm);
        this.numberOftabs = numberOftabs;
    }

    /**
     * Get fragment item.
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        return DataCenter.fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return numberOftabs;
    }
}
