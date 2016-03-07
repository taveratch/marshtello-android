package com.example.taweesoft.marshtello.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Card List model.
 * Created by TAWEESOFT on 2/28/16 AD.
 */
public class CardList extends RealmObject {

    /*Attributes.*/
    private String name;
    private int id;
    private RealmList<Card> cards;

    /**
     * Constructor.
     */
    public CardList() {
    }

    public CardList(String name){
        cards = new RealmList<Card>();
        setName(name);
    }

    public RealmList<Card> getCards() {
        return cards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCards(RealmList<Card> cards) {
        this.cards = cards;
    }

}
