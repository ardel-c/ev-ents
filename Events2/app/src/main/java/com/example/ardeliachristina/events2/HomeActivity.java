package com.example.ardeliachristina.events2;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {


    DatabaseHelper databaseHelper;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        //insert event data
        databaseHelper = new DatabaseHelper(this);


        boolean insert;
        if(databaseHelper.getEventList().size()==0) {
            insert = databaseHelper.insertEvent("EV001","Markplus Conference 2018", "The biggest marketing event " +
                            "in Asia! The 12th Annual MarkPlus Conference 2018 will be held in Jakarta on 7 December 2018 at Grand Ballroom, " +
                            "The Ritz Carlton Jakarta!", "The Ritz-Carlton Jakarta", "7/12/2018",
                    "7/12/2018", 9, -6.228540, 106.827174);
            insert = databaseHelper.insertEvent("EV002","Charity Concert", "Perform by Project Pop, " +
                            "Elephant Kind, Lex Musica, Naomi x Roma, Rumah Belajar Matalangi Yayasan PKBM Al-Falah",
                    "Integrated Faculty Club, UI", "22/12/2018", "22/12/2018",
                    8, -6.351236, 106.830689);
            insert = databaseHelper.insertEvent("EV003","Incubus Lives", "Incubus Live in Jakarta",
                    "Jakarta at JIExpo Kemayoran", "7/2/2018", "7/2/2018", 8,
                    -6.144804, 106.848686);
            insert = databaseHelper.insertEvent("EV004","Comic Con", "Indonesia Comic Con",
                    "Jakarta Convention Center", "28/10/2018", "29/10/2018", 9,
                    -6.214078, 106.807378);
            insert = databaseHelper.insertEvent("EV005","Innovate or Die!!", "Seminar and Workshop",
                    "Mh Thamrin Kav 28-30 Jakarta, Indonesia", "18/1/2018", "18/1/2018", 7,
                    -6.192896, 106.822252);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.viewAllID) {
                Intent viewall = new Intent(getApplicationContext(), AllEventsActivity.class);
                startActivity(viewall);
        }else if(id == R.id.buyID){
            Intent buy = new Intent(getApplicationContext(), BuyActivity.class);
            startActivity(buy);
        }else if(id== R.id.homeID){
            Intent home = new Intent(getApplicationContext(), HomeActivity.class);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(home);
        }else if(id== R.id.logoutID){
            Intent logout = new Intent(getApplicationContext(), MainActivity.class);
            logout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(logout);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if(position == 0){
                FavoriteEventTab fav = new FavoriteEventTab();
                return fav;
            }else if(position ==1){
                MyEventListTab myEvent = new MyEventListTab();
                return myEvent;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show total pages.
            return 2;
        }


    }
}
