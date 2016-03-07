package com.example.taweesoft.marshtello.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.taweesoft.marshtello.ui.fragments.CardDetailFragment;
import com.example.taweesoft.marshtello.ui.fragments.CommentFragment;

/**
 * Card detail pager adapter used in CardDetailActivity.
 * Created by TAWEESOFT on 3/1/16 AD.
 */
public class CardDetailPagerAdapter extends FragmentStatePagerAdapter {

    /*Attributes.*/
    private int tabCount;
    private int card_id , cardList_id;

    /**
     * Constructor.
     * @param fm
     * @param tabCount
     * @param cardList_id
     * @param card_id
     */
    public CardDetailPagerAdapter(FragmentManager fm, int tabCount, int cardList_id, int card_id) {
        super(fm);
        this.tabCount = tabCount;
        this.card_id = card_id;
        this.cardList_id = cardList_id;
    }

    @Override
    public Fragment getItem(int position) {
        if(position ==0){ /*First page is CardDetailFragment.*/
            return new CardDetailFragment(cardList_id, card_id);
        }else if(position == 1) /*Second page is CommentFragment*/
            return new CommentFragment(cardList_id,card_id);
        return null;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
