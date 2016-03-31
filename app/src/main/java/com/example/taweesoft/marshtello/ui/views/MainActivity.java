package com.example.taweesoft.marshtello.ui.views;

import android.app.Dialog;
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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taweesoft.marshtello.events.AlertDialogButtonAction;
import com.example.taweesoft.marshtello.events.AlertDialogFactory;
import com.example.taweesoft.marshtello.events.CustomDialogFactory;
import com.example.taweesoft.marshtello.events.CustomOnClickListener;
import com.example.taweesoft.marshtello.events.DepthPageTransformer;
import com.example.taweesoft.marshtello.events.DialogAction;
import com.example.taweesoft.marshtello.managers.CardManager;
import com.example.taweesoft.marshtello.ui.adapters.CardRVCustomAdapter;
import com.example.taweesoft.marshtello.ui.fragments.CardListFragment;
import com.example.taweesoft.marshtello.ui.holders.RenameCardListDialogHolder;
import com.example.taweesoft.marshtello.utils.Constants;
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
        if(Constants.cardLists.size() == 0)
            initialTabFromStorage();

        /*Update card count and cards in RV*/
        CardList cardList = Constants.cardLists.get(position);
        card_count_txt.setText(cardList.getCards().size()+"");
        cardAdapter = new CardRVCustomAdapter(cardList.getCards(),new RecyclerViewAction(position));
        cardAdapter.addObserver(this);
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
        int tag = (int)data;
        if(tag == Constants.CARD_RV_ADAPTER){ // Notified by CardRVCustomAdapter when remove a card.
            updateCardAdapter();
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
        for (int i=current;i< Constants.cardLists.size()-1;i++)
            bullet+= whiteCircle;
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
        rv.setItemAnimator(new DefaultItemAnimator());

        /*Add action to add_btn*/
        add_card_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewCardActivity.class);
                intent.putExtra("id", cardList_id);
                startActivityForResult(intent, 2);
            }
        });

        /*Add action to remove_cardlist_btn*/
        remove_card_list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogRemovePositiveButtonAction positive = new DialogRemovePositiveButtonAction("Yes , delete");
                DialogRemoveNegativeButtonAction negative = new DialogRemoveNegativeButtonAction("Just clear all card");
                DialogRemoveNeutralButtonAction neutral = new DialogRemoveNeutralButtonAction("No");
                /*Build the dialog.*/
                String title = "Message";
                String content = "Delete or clear this card list ?";
                AlertDialog.Builder builder = AlertDialogFactory.newInstance(MainActivity.this, title, content, positive, negative, neutral);
                builder.create().show();
            }
        });

        edit_card_list_name_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 /*Get card list*/
                final CardList cardList = Constants.cardLists.get(cardList_id);
                /*Define dialog*/
                final Dialog dialog = CustomDialogFactory.newInstance(MainActivity.this,R.layout.rename_card_list_dialog_layout,new EditCardListName(cardList));
                /*show dialog*/
                dialog.show();
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
        if(Constants.cardLists.size() == 0) {
            CardList cardList = new CardList("Empty Card List");
            CardManager.addCardList(this,cardList);
        }

        Log.e("R1", cardList_id + "");
        /*In case that has more that one fragment available*/
        for(int i =0;i< Constants.cardLists.size();i++){
            Constants.fragmentList.add(new CardListFragment(i));
            tabLayout.addTab(tabLayout.newTab().setText(""));
        }
        Log.e("R2", cardList_id + "");
        adapter = new PagerAdapter(getSupportFragmentManager(), Constants.cardLists);
        pager.setAdapter(adapter);
        pager.setPageTransformer(true, new DepthPageTransformer());
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
        Constants.fragmentList.add(new CardListFragment(adapter.getCount()-1));

        /*Add tab to tablayout.*/
        tabLayout.addTab(tabLayout.newTab().setText(""));

        /*Set new adapter and set to ViewPager.*/
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
     * Get card list id from
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null) return;
        int position = data.getIntExtra("position",-1);
        updateUI(position);
        /*From NewCardActivity*/
        if(resultCode == 2){
            Toast.makeText(this, "New card has been added", Toast.LENGTH_SHORT).show();
            rv.scrollToPosition(rv.getAdapter().getItemCount()-1);
        }else{ /*From CardDetailActivity*/
            Toast.makeText(this,"Card has been updated" , Toast.LENGTH_SHORT).show();
        }
    }

    public void updateViewPagerAdapter() {
        adapter.notifyDataSetChanged();
    }

    public void updateUIAfterRemove() {
        int currentPosition = cardList_id;
            /*Clear all CardListFragment*/
        Constants.fragmentList.clear();
        initialTabFromStorage();
        cardList_id = currentPosition;
            /*If the removed CardListFragment is not the last fragment then set position to the previous one.*/
        if (cardList_id < Constants.cardLists.size()) {
            pager.setCurrentItem(cardList_id, true);
            updateListCountBullet(cardList_id);
        }else{
            pager.setCurrentItem(cardList_id-1,true);
            updateListCountBullet(cardList_id - 1);
            cardList_id--;
        }
    }

    public void updateCardAdapter() {
        CardList cardList = Constants.cardLists.get(cardList_id);
        cardAdapter.notifyDataSetChanged();
        card_count_txt.setText(cardList.getCards().size()+"");
    }

    /**
     * Dialog action for custom dialog
     */
    class EditCardListName implements DialogAction{

        private CardList cardList;

        public EditCardListName(CardList cardList) {
            this.cardList = cardList;
        }

        @Override
        public void blind(final Dialog dialog) {
            dialog.setCancelable(false);
            final RenameCardListDialogHolder dialogHolder = new RenameCardListDialogHolder(dialog.getWindow().getDecorView().getRootView());

                /*Set hint to be current card list name*/
            dialogHolder.card_list_name_txt.setHint(cardList.getName());

                /*Rename btn action*/
            dialogHolder.add_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        /*Get new name*/
                    String name = dialogHolder.card_list_name_txt.getText().toString();
                    if (name.length() > 0) {
                             /*Update card list name*/
                        CardManager.renameCardList(MainActivity.this, cardList, name);
                            /*Update ui by using current position.*/
                        updateViewPagerAdapter();
                            /*Show successful message*/
                        Toast.makeText(MainActivity.this, "Card list has been renamed", Toast.LENGTH_SHORT).show();
                            /*dismiss dialog*/
                        dialog.dismiss();
                    } else { /*empty card list name*/
                        Toast.makeText(MainActivity.this, "Please enter card list name", Toast.LENGTH_SHORT).show();
                    }

                }
            });

                /*Cancel btn action*/
            dialogHolder.cancel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        /*dismiss dialog*/
                    dialog.dismiss();
                }
            });
        }
    }

    /**
     * Dialog action
     */
    class DialogRemovePositiveButtonAction implements AlertDialogButtonAction {
        private String buttonText;

        public DialogRemovePositiveButtonAction(String buttonText) {
            this.buttonText = buttonText;
        }

        @Override
        public String getButtonText() {
            return buttonText;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            Context context = MainActivity.this;
            CardManager.removeCardList(MainActivity.this, Constants.cardLists, cardList_id);
            updateUIAfterRemove();
            Toast.makeText(MainActivity.this,"Removed" ,Toast.LENGTH_SHORT).show();
        }
    }
    class DialogRemoveNegativeButtonAction implements AlertDialogButtonAction {
        private String buttonText;

        public DialogRemoveNegativeButtonAction(String buttonText) {
            this.buttonText = buttonText;
        }

        @Override
        public String getButtonText() {
            return buttonText;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            /*Call clear card from Storage.*/
            Context context = MainActivity.this;
            CardManager.clearAllCard(context, Constants.cardLists.get(cardList_id));
            updateCardAdapter();
        }
    }
    class DialogRemoveNeutralButtonAction implements AlertDialogButtonAction {
        private String buttonText;

        public DialogRemoveNeutralButtonAction(String buttonText) {
            this.buttonText = buttonText;
        }

        @Override
        public String getButtonText() {
            return buttonText;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
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
            startActivityForResult(intent, 2);
        }
    }
}
