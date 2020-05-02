package com.hemant.todonotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.hemant.todonotesapp.PrefConstant.IS_LOGGED_IN;

public class LoginActivity extends AppCompatActivity {
EditText editTextFullName, editTextUsername;
Button loginButton;
SharedPreferences sharedPreferences;
SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextFullName= findViewById(R.id.editTextFullName);
        editTextUsername=findViewById(R.id.editTextUsername);
        loginButton=findViewById(R.id.loginButton);
        setupSharedPreferences();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName=editTextFullName.getText().toString();
                String userName=editTextUsername.getText().toString();
                if(!TextUtils.isEmpty(fullName) && !TextUtils.isEmpty(userName)) {
                    Intent intent = new Intent(LoginActivity.this, MyNotesActivity.class);
                    intent.putExtra(AppConstant.FULL_NAME, fullName);
                    startActivity(intent);
                    //here we are logging in
                    saveLoginStatus();
                    saveFullName(fullName);

                }
                else
                {
                    //used for displaying some text for some duration
                    Toast.makeText(LoginActivity.this, "FullName and Username can't be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void saveFullName(String fullName) {
        editor = sharedPreferences.edit();
        editor.putString(PrefConstant.FULL_NAME, fullName);
        editor.apply();
    }

    private void saveLoginStatus() {
      editor=sharedPreferences.edit();
      editor.putBoolean(IS_LOGGED_IN, true);
      editor.apply();
    }

    private void setupSharedPreferences() {
        sharedPreferences=getSharedPreferences(PrefConstant.SHARED_PREERENCE_NAME, MODE_PRIVATE);
    }
}
