package com.example.taweesoft.marshtello;

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

import com.example.taweesoft.marshtello.Fragments.CardListFragment;
import com.example.taweesoft.marshtello.PagerAdapter.PagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.list_count_txt)
    TextView list_count_txt;

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;

    @Bind(R.id.pager)
    ViewPager pager;


    PagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Binding the components
        ButterKnife.bind(this);
        Storage storage = Storage.getInstance();
        storage.loadData(this);
        initialTabFromStorage();
        /*set status bar color*/
        Utilities.setStatusBarColor(this, getResources().getColor(R.color.colorPrimary));

        /*Set icon on action bar*/
        setCustomActionBar();
        //Initial tabs
        initialTabs();

        //Initial adapter and tablayout.
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


    public void initialTabFromStorage(){
        for(int i =0;i<DataCenter.cardLists.size();i++){
            DataCenter.fragmentList.add(new CardListFragment(DataCenter.cardLists.get(i), i));
            tabLayout.addTab(tabLayout.newTab().setText(tabLayout.getTabCount() + 1 + ""));
            adapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
            pager.setAdapter(adapter);
        }
    }
    /**
     * Create new card list
     */
    public void newFragment(){
        final CardList cardList = new CardList("My card");
        Realm.getInstance(this).executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                DataCenter.cardLists.add(cardList);
            }
        });

        DataCenter.fragmentList.add(new CardListFragment(cardList,adapter.getCount()));
        tabLayout.addTab(tabLayout.newTab().setText(tabLayout.getTabCount() + 1 + ""));
        adapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        pager.setAdapter(adapter);
        pager.setCurrentItem(adapter.getCount()-1);
        Storage.getInstance().saveData(this);
        Log.e("ADAPTER", adapter.getCount() + "");
    }

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
