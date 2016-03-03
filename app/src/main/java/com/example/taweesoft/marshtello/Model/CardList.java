package com.example.taweesoft.marshtello.Model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by TAWEESOFT on 2/28/16 AD.
 */
public class CardList extends RealmObject {

    private String name;
    private int id;
    private RealmList<Card> cards;


    public CardList() {
    }

    public CardList(String name){
        cards = new RealmList<Card>();
        setName(name);
    }

//    public void addCard(Card card){
//        cards.add(card);
//    }

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
