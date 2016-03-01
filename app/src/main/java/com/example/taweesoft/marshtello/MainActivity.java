package com.example.taweesoft.marshtello;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.taweesoft.marshtello.Fragments.CardListFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

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

        /*set status bar color*/
        Utilities.setStatusBarColor(this,getResources().getColor(R.color.colorPrimary));

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_actionbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_new_card_list){
            newFragment();
        }
        return super.onOptionsItemSelected(item);
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
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
//        tabLayout.addTab(tabLayout.newTab().setText("Todo"));
//        tabLayout.addTab(tabLayout.newTab().setText("Doing"));
//        tabLayout.addTab(tabLayout.newTab().setText("Done"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    /**
     * Create new card list
     */
    public void newFragment(){
        CardList cardList = new CardList("My card");
        DataCenter.cardLists.add(cardList);
        DataCenter.fragmentList.add(new CardListFragment(cardList,adapter.getCount()));
        tabLayout.addTab(tabLayout.newTab().setText(tabLayout.getTabCount()+1+""));
        adapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        pager.setAdapter(adapter);
        pager.setCurrentItem(adapter.getCount());
        Log.e("ADAPTER" , adapter.getCount()+"");
    }
}
