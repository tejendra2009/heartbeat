package com.oppoindia.billionbeats;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.oppoindia.billionbeats.db.DbHelper;
import com.oppoindia.billionbeats.model.GlobalConst;
import com.oppoindia.billionbeats.model.UserInfo;
import com.oppoindia.billionbeats.ui.login.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;


public class SignUP extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = SignUP.class.getSimpleName();
    UserInfo userInfo;
    EditText editTextName, editTextEmail, editTextPassword, editTextSurname, refferal_code, editTextPassword2;
    String name, email, password, surName, refferalcode, city, password2;
    ProgressBar loadingProgressBar;
    private Button buttonSubmit,disclaimer,fb;
    private AwesomeValidation awesomeValidation;

    private static final int RC_SIGN_IN = 200;
    String referre;
    FirebaseAuth auth;
    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.GoogleBuilder().build(),
            new AuthUI.IdpConfig.FacebookBuilder().build()
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up_sp);
         auth = FirebaseAuth.getInstance();
        loadingProgressBar = findViewById(R.id.loading);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonSubmit = findViewById(R.id.buttonSubmit);
disclaimer=findViewById(R.id.disclaimer);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextName = findViewById(R.id.editTextName);
        refferal_code = findViewById(R.id.refferal_code);

        //editTextSurname = findViewById(R.id.editTextName1);
        editTextPassword2 = findViewById(R.id.editTextCPassword);
        awesomeValidation.addValidation(this, R.id.editTextEmail, Patterns.EMAIL_ADDRESS, R.string.invalid_email);
        // awesomeValidation.addValidation(this, R.id.editTextMobile, GlobalConst.REGEX_MOBILE_NUMBER, R.string.invalid_mobile);
        awesomeValidation.addValidation(this, R.id.editTextPassword, GlobalConst.REGEX_PASSWORD, R.string.invalid_password);
        awesomeValidation.addValidation(this, R.id.editTextName, GlobalConst.REGEX_TEXT_ONLY, R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.editTextName1, GlobalConst.REGEX_TEXT_ONLY, R.string.invalid_city);
        awesomeValidation.addValidation(this, R.id.editTextCPassword, GlobalConst.REGEX_PASSWORD, R.string.invalid_confirm_password);
        final Button loginButton = findViewById(R.id.login);
        final EditText Edittextrefferalfb = findViewById(R.id.edittextrefferalfb);
        fb = findViewById(R.id.buttonfb);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                referre = Edittextrefferalfb.getText().toString();
                Log.e("*****REFFERAL CODE*****", referre);
                startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers).setIsSmartLockEnabled(false).setTheme(R.style.LoginTheme).build(), RC_SIGN_IN);
            }
        });
disclaimer.setOnClickListener(this);
        buttonSubmit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.buttonSubmit){
        //  Log.e("TAG", "Submit=Yes");
        name = editTextName.getText().toString();
        email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();
       // surName = editTextSurname.getText().toString();
        refferalcode = refferal_code.getText().toString();

        password2 = editTextPassword2.getText().toString();

        if (v == buttonSubmit && awesomeValidation.validate()) {
            userInfo = new UserInfo();

            userInfo.setCity("NCR");
            userInfo.setName(name );
            userInfo.setEmail(email);
            // Log.e("TAG", "Submit=" + userInfo.getEmail());

            userInfo.setPassword(password);
            userInfo.setRefferal("");
            userInfo.setPassword2(password2);
            //  userInfo.setRefferal_count("0");
            userInfo.setReffere(refferalcode);
            userInfo.setMobile("7890654321");
            if(Util.isInternetAvailable(ApplicationInit.getAppContext())){
               // DbHelper db = new DbHelper(getApplicationContext());
               registerValue(userInfo, loadingProgressBar);

            }else{
                Toast.makeText(getApplicationContext(), "No Internet Connection.", Toast.LENGTH_LONG).show();
            }


        }}else{
            Intent intent =new Intent(this,Disclaimer.class);
            startActivity(intent);
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                String email = auth.getCurrentUser().getEmail();
                String name = auth.getCurrentUser().getDisplayName();

                UserInfo userInfo = new UserInfo();
                Log.e("*****REFFERAL CODE*****", referre);
                userInfo.setCity("NCR");
                userInfo.setName(name);
                userInfo.setEmail(email);
                // Log.e("TAG", "Submit=" + userInfo.getEmail());

                userInfo.setPassword("12345678");
                userInfo.setRefferal("");
                userInfo.setPassword2("12345678");
                //  userInfo.setRefferal_count("0");
                userInfo.setReffere(referre);
                userInfo.setMobile("7890654321");

                if (Util.isInternetAvailable(ApplicationInit.getAppContext())) {

                    // DbHelper db = new DbHelper(getApplicationContext());
                    registerValue(userInfo,null);

                }
                // ...
            } else {
               // Log.e("REFFERAL ERROR*****", auth.getAccessToken(true).toString());
            }
        }
    }

    private String registerValue(final UserInfo userInfo, final ProgressBar progressBar) {
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
                        if(progressBar!=null){
                        loadingProgressBar.setVisibility(View.GONE);}
                        Log.e(TAG, response.toString());
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ApplicationInit.getAppContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("LOGIN_FLAG", true);
                        editor.putString("email", userInfo.getEmail());
                        editor.putString("name", userInfo.getName());


                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
                if(progressBar!=null){
                    loadingProgressBar.setVisibility(View.GONE);}
               // Log.e(TAG + " ERROR:", error.getLocalizedMessage());
                Toast.makeText(getApplicationContext(), "User Logged-in on another device.", Toast.LENGTH_LONG).show();
                //serverResp.setText("Error getting response");
            }
        });


        ApplicationInit.getAppContext().addToRequestQueue(jsonObjectRequest);
        return responseValue[0];
    }
}
