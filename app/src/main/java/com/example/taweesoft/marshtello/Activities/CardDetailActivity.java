package com.example.taweesoft.marshtello.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.taweesoft.marshtello.PagerAdapter.CardDetailPagerAdapter;
import com.example.taweesoft.marshtello.PagerAdapter.PagerAdapter;
import com.example.taweesoft.marshtello.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CardDetailActivity extends AppCompatActivity {

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;

    @Bind(R.id.pager)
    ViewPager pager;

    private CardDetailPagerAdapter adapter;

    private int cardList_id;
    private int card_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_detail);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        setCustomActionBar();
        initialTabs();
        card_id = getIntent().getIntExtra("card_id",-1);
        cardList_id = getIntent().getIntExtra("cardList_id", -1);

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

    public void setCustomActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        LayoutInflater inflater = LayoutInflater.from(this);
        View customActionBar = inflater.inflate(R.layout.card_detail_actionbar, null);
        ImageView back_img = (ImageView)customActionBar.findViewById(R.id.back_img);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(1);
                finish();
            }
        });
        actionBar.setCustomView(customActionBar);
        actionBar.setDisplayShowCustomEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(1);
    }
}
