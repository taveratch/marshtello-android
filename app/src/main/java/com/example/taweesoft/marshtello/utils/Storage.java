package com.example.taweesoft.marshtello.utils;

import android.content.Context;

import com.example.taweesoft.marshtello.models.Card;
import com.example.taweesoft.marshtello.models.CardList;
import com.example.taweesoft.marshtello.models.DataWrapper;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by TAWEESOFT on 3/1/16 AD.
 */
public class Storage {
    private static Storage storage = null;
    private static Context context;
    /**
     * Get instance of Storage Object.
     * @return
     */
    public static Storage getInstance(Context context1){
        context = context1;
        if(storage == null)
            storage = new Storage();
        return storage;
    }

    /**
     * Save data into internal storage.
     */
    public void saveData(){
        try{
            if(DataCenter.result.size()>0)
                DataCenter.result.clear();
        }catch(IllegalStateException ex){}

        Realm realm = Realm.getInstance(context);
        final DataWrapper dataWrapper = new DataWrapper(DataCenter.cardLists);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(dataWrapper);
            }
        });

    }

    /**
     * Load data from internal storage.
     */
    public void loadData(){
        Realm realm = Realm.getInstance(context);
        RealmResults<DataWrapper> result = realm.where(DataWrapper.class).findAll();
        if(result.size()>0)
            DataCenter.cardLists = result.get(0).getCardListRealmList();
        DataCenter.result = result;
    }

    public void removeAllCardList(){
        Realm.getInstance(context).executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                DataCenter.result.get(0).getCardListRealmList().clear();
            }
        });

    }

    /**
     *
     * @param position = card list position
     */
    public void removeCardList(final int position){
        DataCenter.fragmentList.remove(position);
        Realm.getInstance(context).executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                DataWrapper dataWrapper = DataCenter.result.get(0);
                RealmList<CardList> cardLists = dataWrapper.getCardListRealmList();
                cardLists.remove(position);
            }
        });

    }

    /**
        @param position = card list position.
     */
    public void removeAllCard(final int position){
        Realm.getInstance(context).executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                DataWrapper dataWrapper = DataCenter.result.get(0);
                RealmList<CardList> cardLists = dataWrapper.getCardListRealmList();
                cardLists.get(position).getCards().clear();
            }
        });

    }

    /**
     *
     * @param cardListPosition
     * @param cardPosition
     */
    public void removeCard(final int cardListPosition ,final int cardPosition){
        Realm.getInstance(context).executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                DataWrapper dataWrapper = DataCenter.result.get(0);
                RealmList<CardList> cardLists = dataWrapper.getCardListRealmList();
                RealmList<Card> cards = cardLists.get(cardListPosition).getCards();
                cards.remove(cardPosition);
            }
        });
    }
}
