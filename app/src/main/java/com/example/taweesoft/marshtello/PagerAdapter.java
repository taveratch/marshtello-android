package com.example.taweesoft.marshtello;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.taweesoft.marshtello.Fragments.CardListFragment;

/**
 * Created by TAWEESOFT on 2/27/16 AD.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    private int numberOftabs;

    public PagerAdapter(FragmentManager fm , int numberOftabs) {
        super(fm);
        this.numberOftabs = numberOftabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0 :
                return new CardListFragment(DataCenter.todo);
            case 1 :
                return new CardListFragment(DataCenter.doing);
            case 2 :
                return new CardListFragment(DataCenter.done);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOftabs;
    }
}
