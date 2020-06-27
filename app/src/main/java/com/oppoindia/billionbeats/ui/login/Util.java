package com.oppoindia.billionbeats.ui.login;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Util {
    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }

    }


    public static String getProperty(String key,Context context)  {
        String value="";
        try {
            Properties properties = new Properties();
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("schedule.properties");
            properties.load(inputStream);
            value=properties.getProperty(key);
        }catch (IOException ie){
ie.printStackTrace();
        }
        return value;

    }


   /* public boolean onTouchButton(View v, MotionEvent event ,int drawableId) {
        final Bitmap bitmap;  //Declare bitmap
        bitmap = BitmapFactory.decodeResource(ApplicationInit.getAppContext().getResources(), drawableId);
        int eventPadTouch = event.getAction();
        float iX=event.getX();
        float iY=event.getY();

        switch (eventPadTouch) {

            case MotionEvent.ACTION_DOWN:
                Toast.makeText(ApplicationInit.getAppContext(), "*******Touch on image part******", Toast.LENGTH_LONG).show();
                if (iX>=0 & iY>=0 & iX<bitmap.getWidth() & iY<bitmap.getHeight()) { //Makes sure that X and Y are not less than 0, and no more than the height and width of the image.
                    if (bitmap.getPixel((int) iX, (int) iY)!=0) {
                        Toast.makeText(ApplicationInit.getAppContext(), "*******Touch on image part******", Toast.LENGTH_LONG).show();
                    }
                }
                return true;
        }
        return false;
    }*/
}
