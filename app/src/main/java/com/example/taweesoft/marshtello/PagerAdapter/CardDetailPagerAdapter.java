package com.example.taweesoft.marshtello.PagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.taweesoft.marshtello.Fragments.CardCommentFragment;
import com.example.taweesoft.marshtello.Fragments.CardDetailFragment;

/**
 * Created by TAWEESOFT on 3/1/16 AD.
 */
public class CardDetailPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;
    private int card_id , cardList_id;

    public CardDetailPagerAdapter(FragmentManager fm, int tabCount, int cardList_id, int card_id) {
        super(fm);
        this.tabCount = tabCount;
        this.card_id = card_id;
        this.cardList_id = cardList_id;
    }

    @Override
    public Fragment getItem(int position) {
        if(position ==0){
            return new CardDetailFragment(cardList_id, card_id);
        }else if(position == 1)
            return new CardCommentFragment(cardList_id,card_id);
        return null;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
