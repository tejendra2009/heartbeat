package com.oppoindia.billionbeats;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.oppoindia.billionbeats.db.DbHelper;

public class ApplicationInit extends Application {

    private static ApplicationInit mInstance;
    public static final String TAG = ApplicationInit.class
            .getSimpleName();

    private RequestQueue mRequestQueue;
    public void onCreate(){
        super.onCreate();
        Firebase.setAndroidContext(this);
        FirebaseAuth auth=FirebaseAuth.getInstance();
        new DbHelper(this);
       mInstance = this;
    }

    public static synchronized ApplicationInit getAppContext(){

        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}