package com.example.taweesoft.marshtello.utils;

import com.example.taweesoft.marshtello.ui.fragments.CardListFragment;
import com.example.taweesoft.marshtello.models.CardList;
import com.example.taweesoft.marshtello.models.DataWrapper;
import com.example.taweesoft.marshtello.R;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Stored all data that used in application
 * Created by TAWEESOFT on 2/27/16 AD.
 */
public class Constants {

    /*Tag images*/
    public static int red_circle_img = R.drawable.red_tag;
    public static int blue_circle_img = R.drawable.blue_tag;

    /*TAG number*/
    public static int RED_TAG = 1;
    public static int BLUE_TAG = 2;

    /*Activities TAG*/
    public static int CARD_RV_ADAPTER = 1;
    public static int CARD_LIST_FRAGMENT = 2;
    public static int MAIN_ACTIVITY_EDIT_CARD_LIST = 3;
    public static int MAIN_ACTIVITY_REMOVE_CARD_LIST = 4;

    /*Fragment List*/
    public static List<CardListFragment> fragmentList = new ArrayList<CardListFragment>();

    /*Card List*/
    public static RealmList<CardList> cardLists = new RealmList<CardList>();

    /*RealmResult*/
    public static RealmResults<DataWrapper> result;
}
