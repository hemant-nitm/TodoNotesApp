package com.hemant.todonotesapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MyNotesActivity extends AppCompatActivity {
String fullName;
FloatingActionButton fabAddNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);
        Intent intent =getIntent();
       fullName= intent.getStringExtra("full_name");
       fabAddNotes.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               setupDialogBox();

           }
       });

        getSupportActionBar().setTitle(fullName);
    }

    private void setupDialogBox()
    {
        View view = LayoutInflater.from( MyNotesActivity.this ).inflate( R.layout.add_notes_dialog_layout,null );
        final EditText editTextTitle = view.findViewById( R.id.editTextTitle );
        final EditText editTextDescription = view.findViewById( R.id.editTextDescription );
        Button buttonSubmit = view.findViewById( R.id.buttonSubmit );
        //Dialog i.e. popup
        AlertDialog dialog=new AlertDialog.Builder(this).setView(view).create();
        dialog.show();
    }
}
