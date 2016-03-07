package com.example.taweesoft.marshtello.managers;

import com.example.taweesoft.marshtello.models.Card;

import java.util.Comparator;

/**
 * Compare two cards together.
 * Created by TAWEESOFT on 3/6/16 AD.
 */
public class CardComparator implements Comparator<Card> {

    @Override
    /**
     * Red tag has higher priority than Blue tag.
     */
    public int compare(Card lhs, Card rhs) {
        return lhs.getTag() - rhs.getTag();
    }
}
