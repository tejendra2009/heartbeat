package com.oppoindia.billionbeats.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.oppoindia.billionbeats.ApplicationInit;
import com.oppoindia.billionbeats.Landing;
import com.oppoindia.billionbeats.MainActivity;
import com.oppoindia.billionbeats.R;
import com.oppoindia.billionbeats.model.GlobalConst;
import com.oppoindia.billionbeats.model.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;


public class ProfileFragment extends Fragment {

    TextView email, name, refferal_code;
    Button logout, share,tnc;
    String refferal_code_value = PreferenceManager.getDefaultSharedPreferences(ApplicationInit.getAppContext()).getString("refferal", "");

    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        email = (TextView) view.findViewById(R.id.email);
        name = (TextView) view.findViewById(R.id.editTextName);
        refferal_code = (TextView) view.findViewById(R.id.refferal_code);

        share = (Button) view.findViewById(R.id.share_refferal);
        tnc= (Button) view.findViewById(R.id.tnc);
        email.setText(PreferenceManager.getDefaultSharedPreferences(ApplicationInit.getAppContext()).getString("email", ""));
        name.setText(PreferenceManager.getDefaultSharedPreferences(ApplicationInit.getAppContext()).getString("name", ""));
        refferal_code.setText(refferal_code_value);
        refferal_code.setText(refferal_code_value);
      /*  logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ApplicationInit.getAppContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("LOGIN_FLAG", false);
                editor.putString("email", "");
                editor.putString("name", "");
                editor.putString("refferal", "");
                FirebaseAuth.getInstance().signOut();
                editor.commit();
                Intent intent = new Intent(getActivity(), Landing.class);
                startActivity(intent);
            }
        });*/
tnc.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://oppotermsandconditions.carrd.co/"));
        startActivity(browserIntent);
    }
});
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareImage();
            }
        });
        return view;
    }

    private void shareImage() {
        String shareBody="Team India needs you! Download the OPPO Billion Beats app to share your heartbeat. Use referral code " +refferal_code_value+ " at Sign-Up. #OPPO #BillionBeats http://bit.ly/OPPOBillionBeats ";
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareBody);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Sharing App..."));
    }



/*
    private String registerValue(final UserInfo userInfo) {
        final String[] responseValue = {null};
        String json = new Gson().toJson(userInfo);
        JSONObject mJSONObject = null;
        try {
            mJSONObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, GlobalConst.APIURL + "register", mJSONObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // loadingProgressBar.setVisibility(View.GONE);
                      //  Log.e(TAG, response.toString());
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ApplicationInit.getAppContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("LOGIN_FLAG", true);
                        editor.putString("email", userInfo.getEmail());
                        editor.putString("name", userInfo.getName());
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.putExtra("fragment_tag", "other");
                        startActivity(intent);

                        try {
                            JSONObject jsonObj = new JSONObject(response.toString());
                            editor.putString("refferal", jsonObj.getString("refferal"));
                            editor.commit();
                            //Log.e("REFFERAL",jsonObj.getString("refferal"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  loadingProgressBar.setVisibility(View.GONE);
                // Log.e(TAG + " ERROR:", error.getLocalizedMessage());
               // Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_LONG).show();
                //serverResp.setText("Error getting response");
            }
        });


        ApplicationInit.getAppContext().addToRequestQueue(jsonObjectRequest);
        return responseValue[0];
    }*/

}
