package com.hemant.todonotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
EditText editTextFullName, editTextUserName;
Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextFullName= findViewById(R.id.editTextFullName);
        editTextUserName=findViewById(R.id.editTextUsername);
        loginButton=findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName=editTextFullName.getText().toString();
                String userName=editTextUserName.getText().toString();
                if(!TextUtils.isEmpty(fullName) && !TextUtils.isEmpty(userName)) {
                    Intent intent = new Intent(LoginActivity.this, MyNotesActivity.class);
                    intent.putExtra("full_name", fullName);
                    startActivity(intent);
                }
                else
                {
                    //used for displaying some text for some duration
                    Toast.makeText(LoginActivity.this, "FullName and Username can't be empty", Toast.LENGTH_SHORT);
                }
            }
        });

    }
}
