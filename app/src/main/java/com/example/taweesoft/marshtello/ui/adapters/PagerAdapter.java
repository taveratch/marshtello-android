package com.example.taweesoft.marshtello.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.taweesoft.marshtello.models.CardList;
import com.example.taweesoft.marshtello.utils.DataCenter;

import java.util.Observer;

import io.realm.RealmList;

/**
 * Pager adapter use in MainActivity to show all cardlist.
 * Created by TAWEESOFT on 2/27/16 AD.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    private RealmList<CardList> cardLists;


    public PagerAdapter(FragmentManager fm, RealmList<CardList> cardLists) {
        super(fm);
        this.cardLists = cardLists;
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
        return cardLists.size();
    }
}
