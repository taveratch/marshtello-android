package com.example.taweesoft.marshtello;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by TAWEESOFT on 3/1/16 AD.
 */
public class Storage {
    private static Storage storage = null;

    public static Storage getInstance(){
        if(storage == null)
            storage = new Storage();
        return storage;
    }

    public void saveData(Context context){
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

    public void loadData(Context context){
        Realm realm = Realm.getInstance(context);
        RealmResults<DataWrapper> result = realm.where(DataWrapper.class).findAll();
        if(result.size()>0)
            DataCenter.cardLists = result.get(0).getCardListRealmList();
        DataCenter.result = result;
    }
}
