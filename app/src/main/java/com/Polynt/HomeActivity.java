package com.Polynt;

import android.annotation.TargetApi;
import android.content.Intent;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.Polynt.fragments.BaseContainerFragment;
import com.Polynt.fragments.ProductNameContainerFragment;
import com.Polynt.fragments.TradeNameContainerFragment;
import com.Polynt.fragments.SeriesContainerFragment;
import com.Polynt.helpers.OSHelper;
import com.Polynt.helpers.UIHelper;
import com.Polynt.utils.SharePref;

import java.lang.reflect.Field;


public class HomeActivity extends AppCompatActivity {

    private static final String TAB_1_TAG = "Product Name";
    private static final String TAB_2_TAG = "Series";
    private static final String TAB_3_TAG = "Trade Name";

    public String strCategory;
    private FragmentTabHost mTabHost;

    public Toolbar toolbar;

    @Override
    @TargetApi(21)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


//        if (OSHelper.hasLollipop())
//        {
//            Window window = getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.setStatusBarColor(getResources().getColor(R.color.toolbar_bg_color));
//        }

        Intent intent = getIntent();
        strCategory = intent.getStringExtra("category");

        initLayout();

        SharePref.putString(this, SharePref.TEMP_CLIPBOARD, "");


    }



    @Override
    protected void onStart() {
        super.onStart();
        initToolbar();
    }


    private void initToolbar(){
        if (findViewById(R.id.toolbar) != null){
            setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
            /*if (getSupportActionBar() != null){
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }*/
        }
    }

    @SuppressWarnings("deprecation")
    private void initLayout(){

        toolbar = (Toolbar)findViewById(R.id.toolbar);

        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        View drillIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, mTabHost.getTabWidget(), false);
        ((TextView)drillIndicator.findViewById(R.id.title)).setText("Product Name");
        ((ImageView)drillIndicator.findViewById(R.id.icon)).setImageResource(R.drawable.ic_tab_products);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_1_TAG).setIndicator(drillIndicator), ProductNameContainerFragment.class, null);

        View practiceIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, mTabHost.getTabWidget(), false);
        ((TextView)practiceIndicator.findViewById(R.id.title)).setText("Series");
        ((ImageView)practiceIndicator.findViewById(R.id.icon)).setImageResource(R.drawable.ic_tab_series);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_2_TAG).setIndicator(practiceIndicator), SeriesContainerFragment.class, null);

        View playbookIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, mTabHost.getTabWidget(), false);
        ((TextView)playbookIndicator.findViewById(R.id.title)).setText("Trade Name");
        ((ImageView)playbookIndicator.findViewById(R.id.icon)).setImageResource(R.drawable.ic_tab_trade);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_3_TAG).setIndicator(playbookIndicator), TradeNameContainerFragment.class, null);

        mTabHost.getTabWidget().setDividerDrawable(null);

        /*mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                supportInvalidateOptionsMenu();
            }
        });*/
    }

    public void selectTab(int index){
        if (index == 4){
            mTabHost.setCurrentTab(index);
        }
    }

    public void centerActionBarTitle(){
        TextView titleTextView = null;
        ImageButton navButtonView = null;
        try{
            Field f = toolbar.getClass().getDeclaredField("mTitleTextView");
            f.setAccessible(true);
            titleTextView = (TextView)f.get(toolbar);

            f = toolbar.getClass().getDeclaredField("mNavButtonView");
            f.setAccessible(true);
            navButtonView = (ImageButton)f.get(toolbar);

            DisplayMetrics metrics = getResources().getDisplayMetrics();
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams)titleTextView.getLayoutParams();
            params.width = metrics.widthPixels - navButtonView.getMeasuredWidth() * 2;
            titleTextView.setLayoutParams(params);
            titleTextView.setGravity(Gravity.CENTER);
            titleTextView.setLeft(0);

        } catch (Exception e){

        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        boolean isPopFragment = false;
        String currentTabTag = mTabHost.getCurrentTabTag();
        if(currentTabTag.equals(TAB_1_TAG)){
            isPopFragment = ((BaseContainerFragment)getSupportFragmentManager().findFragmentByTag(TAB_1_TAG)).popFragment();
        } else if (currentTabTag.equals(TAB_2_TAG)){
            isPopFragment = ((BaseContainerFragment)getSupportFragmentManager().findFragmentByTag(TAB_2_TAG)).popFragment();
        } else if (currentTabTag.equals(TAB_3_TAG)){
            isPopFragment = ((BaseContainerFragment)getSupportFragmentManager().findFragmentByTag(TAB_3_TAG)).popFragment();
        }

        if(!isPopFragment){
            finish();
        }
    }

    private void showQuitDialog(){
        UIHelper.showMessageDialogWithCallback(this, "Are you sure to quit?", "YES", "NO", new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                super.onPositive(dialog);
                finish();
            }

            @Override
            public void onNegative(MaterialDialog dialog) {
                super.onNegative(dialog);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
