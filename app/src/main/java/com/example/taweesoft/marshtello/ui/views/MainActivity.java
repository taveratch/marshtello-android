package com.example.taweesoft.marshtello.ui.views;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taweesoft.marshtello.managers.CardManager;
import com.example.taweesoft.marshtello.utils.DataCenter;
import com.example.taweesoft.marshtello.ui.fragments.CardListFragment;
import com.example.taweesoft.marshtello.models.CardList;
import com.example.taweesoft.marshtello.ui.adapters.PagerAdapter;
import com.example.taweesoft.marshtello.R;
import com.example.taweesoft.marshtello.utils.Storage;
import com.example.taweesoft.marshtello.utils.Utilities;

import java.util.Observable;
import java.util.Observer;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Main Activity.
 */
public class MainActivity extends AppCompatActivity implements Observer {

    /*UI Componenets*/
    @Bind(R.id.list_count_txt)
    TextView list_count_txt;

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;

    @Bind(R.id.pager)
    ViewPager pager;

    /*Attributes.*/
    PagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Binding the components*/
        ButterKnife.bind(this);

        /*Load all data from storage and set to ui components*/
        final Storage storage = Storage.getInstance(this);
        storage.loadData();
        initialTabFromStorage();

        /*set status bar color*/
        Utilities.setStatusBarColor(this, getResources().getColor(R.color.colorPrimary));

        /*Set icon on action bar*/
        setCustomActionBar();

        /*Initial Tab's layout*/
        initialTabs();

        /*Initial pager adapter and some actions of tablayout.*/
        adapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
                updateListCountBullet(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        /*Set bullet counter to 0 position.*/
        updateListCountBullet(0);


    }


    /**
     * Observer's method that notified by CardListFragment
     * to update pager when remove a cardlist.
     * @param observable
     * @param data
     */
    @Override
    public void update(Observable observable, Object data) {
        int position = (int)data;

        /*Clear all CardListFragment*/
        DataCenter.fragmentList.clear();

        /*Initialize new CardListFragment*/
        initialTabFromStorage();

        /*If the removed CardListFragment is not the last fragment then set position to the previous one.*/
        if (position < tabLayout.getTabCount()) {
            pager.setCurrentItem(position, true);
            updateListCountBullet(position);
        }
    }

    /**
     * Update list count (Bullets)
     * @param current
     */
    public void updateListCountBullet(int current){
        String whiteCircle = "○";
        String blackCircle = "●";
        String bullet = "";
        for(int i =0;i<current;i++)
            bullet += whiteCircle;
        bullet += blackCircle;
        for (int i=current;i<tabLayout.getTabCount()-1;i++)
            bullet+= whiteCircle;
        list_count_txt.setText(bullet);
    }

    private void initialTabs(){
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }


    /**
     * Initial tabs from Storage.
     */
    public void initialTabFromStorage(){
        /*Remove all tabs*/
        tabLayout.removeAllTabs();

        /*In case that no any fragment available*/
        adapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        pager.setAdapter(adapter);

        /*In case that has more that one fragment available*/
        for(int i =0;i< DataCenter.cardLists.size();i++){
            DataCenter.fragmentList.add(new CardListFragment(DataCenter.cardLists.get(i), i,this));
            tabLayout.addTab(tabLayout.newTab().setText(tabLayout.getTabCount() + 1 + ""));
            adapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
            pager.setAdapter(adapter);
        }
    }

    /**
     * Create new card list
     */
    public void newFragment(){
        /*Create new CardList*/
        final CardList cardList = new CardList("My card");

        /*Add new CardList to DataCenter.*/
        CardManager.addCardList(this,cardList);

        /*Add CardListFragment into DataCenter.*/
        DataCenter.fragmentList.add(new CardListFragment(cardList,adapter.getCount(),this));

        /*Add tab to tablayout.*/
        tabLayout.addTab(tabLayout.newTab().setText(tabLayout.getTabCount() + 1 + ""));

        /*Set new adapter and set to ViewPager.*/
        adapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        pager.setAdapter(adapter);
        pager.setCurrentItem(adapter.getCount()-1,true);

        /*Save data to Realm storage.*/
        Storage.getInstance(this).saveData();
    }


    /**
     * Set custom actionbar.
     */
    public void setCustomActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        LayoutInflater inflater = LayoutInflater.from(this);
        View customActionBar = inflater.inflate(R.layout.custom_main_actionbar, null);
        ImageView newCardList_img = (ImageView)customActionBar.findViewById(R.id.new_card_list_img);
        newCardList_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newFragment();
            }
        });
        actionBar.setCustomView(customActionBar);
        actionBar.setDisplayShowCustomEnabled(true);
    }
}
