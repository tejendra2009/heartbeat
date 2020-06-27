package com.oppoindia.billionbeats;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.oppoindia.billionbeats.ui.login.LoginActivity;

public class Landing extends AppCompatActivity implements View.OnClickListener {
    Button login, signnup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        login = (Button) findViewById(R.id.login);
        signnup = (Button) findViewById(R.id.signup);
        login.setOnClickListener(this);
        signnup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == login) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("fragment_tag", "Heart");
            startActivity(intent);
        } else if (v == signnup) {
            Intent intent = new Intent(getApplicationContext(), SignUP.class);
            startActivity(intent);
        }
    }
}
