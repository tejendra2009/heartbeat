package com.oppoindia.billionbeats;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.oppoindia.billionbeats.fragment.CalendarFragment;
import com.oppoindia.billionbeats.fragment.HeartFragment;
import com.oppoindia.billionbeats.fragment.LeaderBoardFragment;
import com.oppoindia.billionbeats.fragment.ProfileFragment;
import com.oppoindia.billionbeats.fragment.ShareFragment;

public class MainActivity extends AppCompatActivity {

    private ActionBar toolbar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_profile:
                   // mTextMessage.setText(R.string.title_home);
                   // toolbar.setTitle("Profile");
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_heart:
                  //  mTextMessage.setText(R.string.title_dashboard);
                   // toolbar.setTitle("Heart");
                    fragment = new HeartFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_share:
                  //  mTextMessage.setText(R.string.title_share);
                   // toolbar.setTitle("Share");
                    fragment = new ShareFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_leaderboard:
                   // toolbar.setTitle("LeaderBoard");
                    fragment = new LeaderBoardFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_calendar:
                  //  mTextMessage.setText(R.string.title_calendar);
                  //  toolbar.setTitle("Schedule");
                    fragment = new CalendarFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_w);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // attaching bottom sheet behaviour - hide / show on scroll
       // CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        //layoutParams.setBehavior(new BottomNavigationBehavior());

        navigation.setItemIconTintList(null);
        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("fragment_tag")!= null &&bundle.getString("fragment_tag").equalsIgnoreCase("shareFragment"))
        {

            loadFragment(new ShareFragment());
        }else{

            loadFragment(new HeartFragment());
        }
        // load the store fragment by default

    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment,"fragment_tag");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onPause(){
super.onPause();
Measure.progressStatus.set(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Measure.progressStatus.set(0);
    }
}
