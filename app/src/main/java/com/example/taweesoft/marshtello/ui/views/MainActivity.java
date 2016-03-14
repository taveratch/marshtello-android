package com.example.taweesoft.marshtello.ui.views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taweesoft.marshtello.events.CustomOnClickListener;
import com.example.taweesoft.marshtello.events.DepthPageTransformer;
import com.example.taweesoft.marshtello.events.ZoomOutPageTransformer;
import com.example.taweesoft.marshtello.managers.CardManager;
import com.example.taweesoft.marshtello.ui.adapters.CardRVCustomAdapter;
import com.example.taweesoft.marshtello.ui.fragments.CardListFragment;
import com.example.taweesoft.marshtello.utils.DataCenter;
import com.example.taweesoft.marshtello.models.CardList;
import com.example.taweesoft.marshtello.ui.adapters.PagerAdapter;
import com.example.taweesoft.marshtello.R;
import com.example.taweesoft.marshtello.utils.Storage;
import com.example.taweesoft.marshtello.utils.Utilities;

import java.util.Observable;
import java.util.Observer;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Main Activity.
 */
public class MainActivity extends AppCompatActivity implements Observer {

    /*UI Componenets*/
//    @Bind(R.id.list_count_txt)
//    TextView list_count_txt;

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;

    @Bind(R.id.pager)
    ViewPager pager;

    @Bind(R.id.card_count_txt)
    TextView card_count_txt;

    @Bind(R.id.tv_card)
    TextView tv_card;

    @Bind(R.id.add_card_btn)
    RelativeLayout add_card_btn;

    @Bind(R.id.edit_card_list_name_btn)
    RelativeLayout edit_card_list_name_btn;

    @Bind(R.id.remove_card_list_btn)
    RelativeLayout remove_card_list_btn;

    @Bind(R.id.rv)
    RecyclerView rv;

    /*Attributes.*/
    PagerAdapter adapter;
    private CardRVCustomAdapter cardAdapter;
    private int cardList_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_list_layout_new);

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

        /*Initial components*/
        initComponents();

        /*Initial pager adapter and some actions of tablayout.*/
//        adapter = new PagerAdapter(getSupportFragmentManager(),DataCenter.cardLists.size());
//        pager.setAdapter(adapter);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateUI(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateUI(tab.getPosition());
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

    public void updateUI(int position){
        Log.e("updateUI",position + "");
        cardList_id = position;

        /*If no cardlist avaible then call initialFromStorage to create an empty one*/
        if(DataCenter.cardLists.size() == 0)
            initialTabFromStorage();

        /*Update card count and cards in RV*/
        CardList cardList = DataCenter.cardLists.get(position);
        card_count_txt.setText(cardList.getCards().size()+"");
        cardAdapter = new CardRVCustomAdapter(cardList.getCards(),new RecyclerViewAction(position));
        rv.setAdapter(cardAdapter);
        updateListCountBullet(position);
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
        if (position < DataCenter.cardLists.size()) {
            pager.setCurrentItem(position, true);
            updateListCountBullet(position);
        }else{
            pager.setCurrentItem(position-1,true);
            updateListCountBullet(position-1);
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
        for (int i=current;i<DataCenter.cardLists.size()-1;i++)
            bullet+= whiteCircle;
//        list_count_txt.setText(bullet);
    }

    private void initialTabs(){
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }



    public void initComponents(){
        /*Set fonts*/
        Typeface normal = Utilities.getNormalFont(this);
        Typeface bold = Utilities.getBoldFont(this);
        Utilities.applyFont(bold,card_count_txt);
        Utilities.applyFont(normal,tv_card);

        /*Initialize RecyclerView*/
        rv.setHasFixedSize(true);
        RecyclerView.LayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        /*Add action to add_btn*/
        add_card_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewCardActivity.class);
                intent.putExtra("id", cardList_id);
                startActivityForResult(intent, 1);
            }
        });

        /*Add action to remove_cardlist_btn*/
        remove_card_list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Build the dialog.*/
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Message");
                dialog.setMessage("Delete or clear this card list ?");
                dialog.setPositiveButton("Yes, delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Context context = MainActivity.this;
                        CardManager.removeCardList(MainActivity.this,DataCenter.cardLists,cardList_id);
                        update(null, cardList_id);
                        Toast.makeText(MainActivity.this,"Removed" ,Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setNeutralButton("NO", null);
                /*Clear all card in the cardlist.*/
                dialog.setNegativeButton("Just clear all card", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*Call clear card from Storage.*/
                        Context context = MainActivity.this;
                        CardManager.clearAllCard(context,DataCenter.cardLists.get(cardList_id));
                        update(null, cardList_id);
                    }
                });

                /*Create dialog and show.*/
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });
    }
    /**
     * Initial tabs from Storage.
     */
    public void initialTabFromStorage(){
        /*Remove all tabs*/
        tabLayout.removeAllTabs();

        /*In case that no any fragment available*/
        if(DataCenter.cardLists.size() == 0) {
            CardList cardList = new CardList("Empty Card List");
            CardManager.addCardList(this,cardList);
        }

        /*In case that has more that one fragment available*/
        for(int i =0;i< DataCenter.cardLists.size();i++){
            DataCenter.fragmentList.add(new CardListFragment(i));
            tabLayout.addTab(tabLayout.newTab().setText(""));

        }
        adapter = new PagerAdapter(getSupportFragmentManager(),DataCenter.cardLists);
        pager.setAdapter(adapter);
        pager.setPageTransformer(true, new DepthPageTransformer());
        if(adapter.getCount() > 0)
            updateUI(0);
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
        DataCenter.fragmentList.add(new CardListFragment(adapter.getCount()-1));

        /*Add tab to tablayout.*/
        tabLayout.addTab(tabLayout.newTab().setText(""));

        /*Set new adapter and set to ViewPager.*/
//        adapter = new PagerAdapter(getSupportFragmentManager(),DataCenter.cardLists.size());
//        pager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        pager.setCurrentItem(adapter.getCount() - 1, true);
        /*Save data to Realm storage.*/
        Storage.getInstance(this).saveData();
    }


    /**
     * Set custom actionbar.
     */
    public void setCustomActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setElevation(0);
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

    /**
     * Set listview action
     */
    class RecyclerViewAction implements CustomOnClickListener {

        private int cardList_id;

        public RecyclerViewAction(int cardList_id) {
            this.cardList_id = cardList_id;
        }

        @Override
        public void onClick(View view, int position) {
            Log.e("Process" ,"Clicked on card " + position);
            Intent intent = new Intent(MainActivity.this, CardDetailActivity.class);
            intent.putExtra("card_id", position);
            intent.putExtra("cardList_id", cardList_id);
            startActivityForResult(intent, 1);
        }
    }

    /**
     * Get card list id from
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("XXXX" , data+"");
        int position = data.getIntExtra("position",-1);
        updateUI(position);
    }
}
