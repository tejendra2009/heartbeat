package com.oppoindia.billionbeats;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Splash extends Activity {

    private static int SPLASH_TIME_OUT = 5000;
    ImageView logo;

   // boolean flag = PreferenceManager.getDefaultSharedPreferences(ApplicationInit.getAppContext()).getBoolean("LOGIN_FLAG", false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo=(ImageView)findViewById(R.id.imgLogo);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */


            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                Intent intent = new Intent(Splash.this, VideoPlayer.class);
               // intent.putExtra("fragment_tag", "other");
                startActivity(intent);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.animation);
        logo.startAnimation(myanim);
    }
}
