package com.oppoindia.billionbeats;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class VideoPlayer extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;

    private View mContentView;

    private View mControlsView;

    private boolean mVisible;

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */

    Uri video;
    VideoView mVideoView;
    int time=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_player);
      //final  boolean flag = PreferenceManager.getDefaultSharedPreferences(ApplicationInit.getAppContext()).getBoolean("LOGIN_FLAG", false);
        mVisible = true;
        Button shareButton = findViewById(R.id.disclaimer);

shareButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
watchYoutubeVideo("qT8Qrw4s2fw");
    }
});
        // Set up the user interaction to manually show or hide the system UI.


        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.

    }
    public  void watchYoutubeVideo(String id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.youtube.com/watch?v=" + id));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
 //time=mVideoView.getDuration();

    }

    @Override
    protected void onResume() {

        super.onResume();
      // mVideoView.start();
    }

    @Override
    public void onStop(){
        super.onStop();
//mVideoView.stopPlayback();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
      //  mVideoView.start();

    }

}









