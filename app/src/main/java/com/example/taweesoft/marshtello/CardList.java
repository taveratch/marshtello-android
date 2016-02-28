package com.example.taweesoft.marshtello;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TAWEESOFT on 2/28/16 AD.
 */
public class CardList {

    private String name;
    private List<Card> cards;

    public CardList(String name){
        cards = new ArrayList<>();
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
