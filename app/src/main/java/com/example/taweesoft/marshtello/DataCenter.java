package com.example.taweesoft.marshtello;

import com.example.taweesoft.marshtello.Fragments.CardListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TAWEESOFT on 2/27/16 AD.
 */
public class DataCenter {

    public static int RED_TAG = 1;
    public static int BLUE_TAG = 2;
    public static List<Card> todo;
    public static List<Card> doing;
    public static List<Card> done;

    public static List<CardListFragment> fragmentList = new ArrayList<CardListFragment>();

    public static List<CardList> cardLists = new ArrayList<CardList>();
}
