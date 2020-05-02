package com.hemant.todonotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setupSharedPreference();
        checkLoginStatus();
    }

    private void setupSharedPreference() {
        sharedPreferences=getSharedPreferences(PrefConstant.SHARED_PREERENCE_NAME, MODE_PRIVATE);
    }

    private void checkLoginStatus() {
        //it will check the status of user login
        boolean isLoggedIn=sharedPreferences.getBoolean(PrefConstant.IS_LOGGED_IN, false);
        if(isLoggedIn)
        {
            //notes Activity
            Intent intent = new Intent(SplashActivity.this, MyNotesActivity.class);
            startActivity(intent);
        }
        else
        {
            //login Activity
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
