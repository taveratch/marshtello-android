package com.example.taweesoft.marshtello;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by TAWEESOFT on 2/28/16 AD.
 */
public class CardList extends RealmObject {

    private String name;
    private RealmList<Card> cards;

    public CardList(String name){
        cards = new RealmList<Card>();
        setName(name);
    }

    public void addCard(Card card){
        cards.add(card);
    }

    public List<Card> getCards() {
        return cards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
