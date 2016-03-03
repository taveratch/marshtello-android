package com.example.taweesoft.marshtello.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.taweesoft.marshtello.PagerAdapter.CardDetailPagerAdapter;
import com.example.taweesoft.marshtello.PagerAdapter.PagerAdapter;
import com.example.taweesoft.marshtello.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CardDetailActivity extends AppCompatActivity {

    /**
     * UI Components
     */
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;

    @Bind(R.id.pager)
    ViewPager pager;

    /*Attributes*/
    private CardDetailPagerAdapter adapter;
    private int cardList_id;
    private int card_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_detail);
        ButterKnife.bind(this);
        /*Get data from CardListFragment*/
        card_id = getIntent().getIntExtra("card_id",-1);
        cardList_id = getIntent().getIntExtra("cardList_id", -1);

        /*Hide action bar.*/
        getSupportActionBar().hide();
        initialTabs();

    }

    private void initialTabs(){
        tabLayout.addTab(tabLayout.newTab().setText("Card"));
        tabLayout.addTab(tabLayout.newTab().setText("Comments"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initial adapter and tablayout.
        adapter = new CardDetailPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount(),cardList_id,card_id);
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());

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
     * Set result back to CardListFragment and destroy.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(1);
    }
}
