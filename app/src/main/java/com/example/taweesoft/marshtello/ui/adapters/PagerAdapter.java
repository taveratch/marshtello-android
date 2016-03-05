package com.example.taweesoft.marshtello.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.taweesoft.marshtello.utils.DataCenter;

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
        return DataCenter.fragmentList.get(position);
//        switch(position){
//            case 0 :
//                return new CardListFragment(DataCenter.todo);
//            case 1 :
//                return new CardListFragment(DataCenter.doing);
//            case 2 :
//                return new CardListFragment(DataCenter.done);
//            default:
//                return null;
//        }
    }

    @Override
    public int getCount() {
        return numberOftabs;
    }
}
