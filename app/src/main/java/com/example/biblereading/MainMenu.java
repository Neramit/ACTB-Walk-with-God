package com.example.biblereading;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;

import com.example.biblereading.Adapter.PagerAdapter;
import com.example.biblereading.Fragment.AnnouceFragment;
import com.example.biblereading.Fragment.DevotionFragment;
import com.example.biblereading.Fragment.GameFragment;
import com.example.biblereading.Fragment.LocationFragment;
import com.example.biblereading.Fragment.PrayFragment;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

//import android.support.v4.view.PagerAdapter;

public class MainMenu extends AppCompatActivity implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

    private TabHost mTabHost;
    private ViewPager mViewPager;
    private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, MainMenu.TabInfo>();
    Menu menu;
    private FirebaseAuth mAuth;

    private class TabInfo {
        private String tag;
        private Class<?> clss;
        private Bundle args;
        private Fragment fragment;

        TabInfo(String tag, Class<?> clazz, Bundle args) {
            this.tag = tag;
            this.clss = clazz;
            this.args = args;
        }
    }

    private class TabFactory implements TabHost.TabContentFactory {

        private final Context mContext;

        /**
         * @param context
         */
        TabFactory(Context context) {
            mContext = context;
        }

        /**
         * (non-Javadoc)
         *
         * @see android.widget.TabHost.TabContentFactory#createTabContent(java.lang.String)
         */
        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
//        getActionBar().setElevation(0);
        getSupportActionBar().setTitle(R.string.Announce);
//        getMenuInflater().inflate(R.menu.main_menu, menu);
        //Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Initialise the TabHost
        this.initialiseTabHost(savedInstanceState);
        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab")); //set the tab as per the saved state
        }
        // Intialise ViewPager
        this.intialiseViewPager();
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("tab", mTabHost.getCurrentTabTag()); //save the tab selected
        super.onSaveInstanceState(outState);
    }

    /**
     * Initialise ViewPager
     */
    private void intialiseViewPager() {

        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, AnnouceFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, PrayFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, DevotionFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, LocationFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, GameFragment.class.getName()));
        PagerAdapter mPagerAdapter = new PagerAdapter(super.getSupportFragmentManager(), fragments);
        //
        this.mViewPager = (ViewPager) super.findViewById(R.id.viewpager);
        this.mViewPager.setAdapter(mPagerAdapter);
        this.mViewPager.setOnPageChangeListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initialiseTabHost(Bundle args) {
//        TabWidget tabWidget = (TabWidget) findViewById(R.id.tabs);
        mTabHost = (TabHost) findViewById(R.id.tabHost);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = sp.getString("Theme", "Green");
        mTabHost.setup();
//        switch (theme) {
//            case "Blue":
//                setTheme(R.style.Blue_NoActionBar);
//                mTabHost.getTabWidget().setBackgroundColor(getResources().getColor(R.color.colorPrimary_blue, null));
//                break;
//            case "Pink":
//                setTheme(R.style.Pink_NoActionBar);
//                mTabHost.getTabWidget().setBackgroundColor(getResources().getColor(R.color.colorPrimary_pink, null));
//                break;
//            case "Orange":
//                setTheme(R.style.Orange_NoActionBar);
//                mTabHost.getTabWidget().setBackgroundColor(getResources().getColor(R.color.colorPrimary_orange, null));
//                break;
//            default:
//                setTheme(R.style.AppTheme_NoActionBar);
//                mTabHost.getTabWidget().setBackgroundColor(getResources().getColor(R.color.colorPrimary, null));
//                break;
//        }

        TabInfo tabInfo = null;
        MainMenu.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("Tab1").setIndicator("", getDrawable(R.drawable.annouce_selector)), (tabInfo = new TabInfo("Tab1", AnnouceFragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        MainMenu.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("Tab2").setIndicator("", getDrawable(R.drawable.pray_selector)), (tabInfo = new TabInfo("Tab2", PrayFragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        MainMenu.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("Tab3").setIndicator("", getDrawable(R.drawable.devotion_selector)), (tabInfo = new TabInfo("Tab3", DevotionFragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        MainMenu.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("Tab4").setIndicator("", getDrawable(R.drawable.location_selector)), (tabInfo = new TabInfo("Tab4", LocationFragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        MainMenu.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("Tab5").setIndicator("", getDrawable(R.drawable.game_selector)), (tabInfo = new TabInfo("Tab5", GameFragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        // Default to first tab
//        this.onTabChanged("Tab1");
        //
        mTabHost.setOnTabChangedListener(this);
    }

    private static void AddTab(MainMenu activity, TabHost tabHost, TabHost.TabSpec tabSpec, TabInfo tabInfo) {
        // Attach a Tab view factory to the spec
        tabSpec.setContent(activity.new TabFactory(activity));
        tabHost.addTab(tabSpec);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.mTabHost.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_log_out) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.title_logout);
            builder.setMessage(R.string.message_logout);

            builder.setPositiveButton(R.string.Yes_logout, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    signOut();
                    startActivity(new Intent(MainMenu.this, LoginActivity.class));
                    finish();
                }
            });
            builder.setNegativeButton(R.string.No_logout, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }
        if (id == R.id.action_profile) {
            startActivity(new Intent(this,ProfileActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabChanged(String s) {
        int pos = this.mTabHost.getCurrentTab();
        this.mViewPager.setCurrentItem(pos);
        switch (s) {
            case "Tab1":{
                this.mTabHost.setCurrentTab(0);
                getSupportActionBar().setTitle(R.string.Announce);
                break;}
            case "Tab2":{
                this.mTabHost.setCurrentTab(1);
                getSupportActionBar().setTitle(R.string.Pray);
                break;}
            case "Tab3":{
                this.mTabHost.setCurrentTab(2);
                getSupportActionBar().setTitle(R.string.Devotion);
                break;}
            case "Tab4":{
                this.mTabHost.setCurrentTab(3);
                getSupportActionBar().setTitle(R.string.Location);
                break;}
            case "Tab5":{
                this.mTabHost.setCurrentTab(4);
                getSupportActionBar().setTitle(R.string.Game);
                break;}
        }
//        int pos = this.mTabHost.getCurrentTab();
//        this.mViewPager.setCurrentItem(pos);
//        this.menu.clear();
//        if (Position == 0) {
//            getMenuInflater().inflate(R.menu.main_menu, menu);
//        } else if (Position == 1) {
//            getMenuInflater().inflate(R.menu.group_menu, menu);
//        }
    }

    public void signOut() {
        mAuth.signOut();
        LoginManager.getInstance().logOut();
//        updateUI(null);
    }

    @Override
        public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.title_exit);
        builder.setIcon(R.drawable.icon_app);
        builder.setMessage(R.string.message_exit);

        builder.setPositiveButton(R.string.Yes_exit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                finishAndRemoveTask();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity();
                }
                System.exit(0);
                int pid = Process.myPid();
                Process.killProcess(pid);
            }
        });
        builder.setNegativeButton(R.string.No_exit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
