package com.oppoindia.billionbeats.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.StringRes;
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
import com.android.volley.toolbox.StringRequest;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.oppoindia.billionbeats.ApplicationInit;
import com.oppoindia.billionbeats.MainActivity;
import com.oppoindia.billionbeats.R;
import com.oppoindia.billionbeats.SignUP;
import com.oppoindia.billionbeats.db.DbHelper;
import com.oppoindia.billionbeats.model.GlobalConst;
import com.oppoindia.billionbeats.model.LoginVO;
import com.oppoindia.billionbeats.model.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final String PATH_TOS = "";
    private static final int RC_SIGN_IN = 200;
    String referre;
    FirebaseAuth auth;
    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.GoogleBuilder().build(),
            new AuthUI.IdpConfig.FacebookBuilder().build()
    );
    // private LoginViewModel loginViewModel;
    private AwesomeValidation awesomeValidation,awesomeValidation1;
    private Button fb;
    private Button forgotPassword;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        //  loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
        //         .get(LoginViewModel.class);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation1 = new AwesomeValidation(ValidationStyle.BASIC);
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final EditText Edittextrefferalfb = findViewById(R.id.edittextrefferalfb);
        awesomeValidation1.addValidation(this, R.id.username, Patterns.EMAIL_ADDRESS, R.string.invalid_email);
        //awesomeValidation.addValidation(this, R.id.username, Patterns.EMAIL_ADDRESS, R.string.invalid_email);
        awesomeValidation.addValidation(this, R.id.username, Patterns.EMAIL_ADDRESS, R.string.invalid_email);
        awesomeValidation.addValidation(this, R.id.password, GlobalConst.REGEX_PASSWORD, R.string.invalid_password);
        final Button loginButton = findViewById(R.id.login);
        fb = findViewById(R.id.buttonfb);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        forgotPassword=findViewById(R.id.forgotpassword);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                referre = Edittextrefferalfb.getText().toString();

                startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers).setIsSmartLockEnabled(false).setTheme(R.style.LoginTheme).build(), RC_SIGN_IN);
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

if(awesomeValidation1.validate() && Util.isInternetAvailable(getApplicationContext()) ){

    forgotPassword(usernameEditText.getText().toString(),loadingProgressBar);

}


            }
        });


        // final Button register = findViewById(R.id.sign_up);

        //  loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
           /* @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });*/

        /*TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }*/

           /* @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };*/
        /*usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });*/

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginVO login = new LoginVO();
                login.setEmail(usernameEditText.getText().toString());
                login.setPassword(passwordEditText.getText().toString());

                if (awesomeValidation.validate()) {

                    int count = new DbHelper(getApplicationContext()).getLogin(login);
                    if (count == 1) {

                        routeHome();
                    } else {
                        if (Util.isInternetAvailable(ApplicationInit.getAppContext())) {
                            loginUser(login, loadingProgressBar);
                        } else {
                            Toast.makeText(getApplicationContext(), "No Internet Connection.", Toast.LENGTH_LONG).show();
                        }

                        // Log.e(TAG,response);
                        // Toast.makeText(getApplicationContext(), "User Name or password incorrect", Toast.LENGTH_LONG).show();
                    }
                }
            }
              /* Call<UserInfo> call = apiService.userSignIn(userDetail);
                call.enqueue(new Callback<UserInfo>() {
                    @Override
                    public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                        int statusCode = response.code();
                      UserInfo  userInfo = response.body();
                        Log.e("Response", userInfo.getEmail());
//Result result=new Result.Success<>(user_info);

                    }

                    @Override
                    public void onFailure(Call<UserInfo> call, Throwable t) {
                        // Log error here since request failed
                        Log.e("Response", t.toString());
                    }
                });
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());*//*
            }}*/
        });




        /*register.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){
                // loadingProgressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(LoginActivity.this, SignUP.class);
                startActivity(intent);
            }
            });*/

    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Intent intent = new Intent(LoginActivity.this, SignUP.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private String loginUser(final LoginVO login, final ProgressBar progressBar) {
        final String[] responseValue = {null};
        String json = new Gson().toJson(login);
        progressBar.setVisibility(View.VISIBLE);
        JSONObject mJSONObject = null;
        try {
            mJSONObject = new JSONObject(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, GlobalConst.APIURL + "login", mJSONObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        Log.e(TAG, response.toString());
                        try {
                            JSONObject jsonObj = new JSONObject(response.toString());
                            String name = jsonObj.getString("name");
                            String email = jsonObj.getString("email");
                            String refferal = jsonObj.getString("refferal");
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ApplicationInit.getAppContext());
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("LOGIN_FLAG", true);
                            editor.putString("email", email);
                            editor.putString("name", name);
                            editor.putString("refferal", refferal);
                            editor.commit();
                            routeHome();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // serverResp.setText("String Response : "+ response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                // Log.e(TAG + " ERROR:", String.valueOf(error.networkResponse.data[0]));
                Toast.makeText(getApplicationContext(), "UserName or Password incorrect", Toast.LENGTH_LONG).show();
                //serverResp.setText("Error getting response");
            }
        });


        ApplicationInit.getAppContext().addToRequestQueue(jsonObjectRequest);
        return responseValue[0];
    }


    private String forgotPassword(final String email, final ProgressBar progressBar) {
        final String[] responseValue = {null};

        progressBar.setVisibility(View.VISIBLE);

        StringRequest String_Request = new StringRequest(Request.Method.GET, GlobalConst.APIURL + "forgetpassword?email="+email,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {




                        progressBar.setVisibility(View.GONE);
                        Log.e(TAG, response);
                        try {

                            Toast.makeText(getApplicationContext(), "Please check your email inbox for a password.", Toast.LENGTH_LONG).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // serverResp.setText("String Response : "+ response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                // Log.e(TAG + " ERROR:", String.valueOf(error.networkResponse.data[0]));
                Toast.makeText(getApplicationContext(), "Couldn't able to find your account on Billion Beats. Please Sign Up", Toast.LENGTH_LONG).show();
                //serverResp.setText("Error getting response");
            }
        });


        ApplicationInit.getAppContext().addToRequestQueue(String_Request);
        return responseValue[0];
    }


    private void routeHome() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("fragment_tag", "other");
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            if (resultCode == RESULT_OK) {
                IdpResponse response = IdpResponse.fromResultIntent(data);
                // Successfully signed in
                //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String email = auth.getCurrentUser().getEmail();
                String name = auth.getCurrentUser().getDisplayName();

                UserInfo user = new UserInfo();

                user.setCity("NCR");
                user.setName(name);
                user.setEmail(email);
                // Log.e("TAG", "Submit=" + userInfo.getEmail());

                user.setPassword("1234567890");
                user.setRefferal("");
                user.setPassword2("1234567890");
                //  userInfo.setRefferal_count("0");
                user.setReffere(referre);
                user.setMobile("9876543210");


                // DbHelper db = new DbHelper(getApplicationContext());
                registerValue(user);

                // ...
            } else {
                //Log.e("REFFERAL ERROR*****", auth.getAccessToken(true).toString());
            }
        }
    }

    private String registerValue(final UserInfo userInfo) {
        final String[] responseValue = {null};
        String json = new Gson().toJson(userInfo);
        JSONObject mJSONObject = null;
        try {
            mJSONObject = new JSONObject(json);
            Log.e("JSON", mJSONObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, GlobalConst.APIURL + "register", mJSONObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // loadingProgressBar.setVisibility(View.GONE);
                        Log.e(TAG, response.toString());
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ApplicationInit.getAppContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("LOGIN_FLAG", true);
                        editor.putString("email", userInfo.getEmail());
                        editor.putString("name", userInfo.getName());


                        try {


                            JSONObject jsonObj = new JSONObject(response.toString());
                            editor.putString("refferal", jsonObj.getString("refferal"));
                            editor.commit();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("fragment_tag", "other");
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "User Logged-in on another device.", Toast.LENGTH_LONG).show();

            }
        });


        ApplicationInit.getAppContext().addToRequestQueue(jsonObjectRequest);
        return responseValue[0];
    }



}
