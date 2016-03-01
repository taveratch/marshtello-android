package com.example.taweesoft.marshtello;

import com.example.taweesoft.marshtello.Fragments.CardListFragment;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Stored all data that used in application
 * Created by TAWEESOFT on 2/27/16 AD.
 */
public class DataCenter {

    /*Tag images*/
    public static int red_circle_img = R.drawable.red_circle;
    public static int blue_circle_img = R.drawable.blue_circle;

    /*TAG number*/
    public static int RED_TAG = 1;
    public static int BLUE_TAG = 2;

    /*Fragment List*/
    public static List<CardListFragment> fragmentList = new ArrayList<CardListFragment>();

    /*Card List*/
    public static RealmList<CardList> cardLists = new RealmList<CardList>();

    /*RealmResult*/
    public static RealmResults<DataWrapper> result;
}
