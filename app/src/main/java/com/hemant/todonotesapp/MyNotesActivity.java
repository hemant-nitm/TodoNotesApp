package com.hemant.todonotesapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hemant.todonotesapp.adapter.NotesAdapter;
import com.hemant.todonotesapp.clicklisteners.ItemClickListener;
import com.hemant.todonotesapp.model.Notes;

import java.util.ArrayList;

import static com.hemant.todonotesapp.AppConstant.DESCRIPTION;
import static com.hemant.todonotesapp.AppConstant.TITLE;

public class MyNotesActivity extends AppCompatActivity {
String fullName;
FloatingActionButton fabAddNotes;
SharedPreferences sharedPreferences;
String TAG="MyNotesActivity";
RecyclerView recyclerViewNotes;
ArrayList<Notes> notesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);
        bindView();
        setupSharedPreference();
        getIntentData();

       fabAddNotes.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               setupDialogBox();
        Log.d("My notes activity", "on click performed");
           }
       });

        getSupportActionBar().setTitle(fullName);
    }

    private void setupSharedPreference() {
        sharedPreferences=getSharedPreferences(PrefConstant.SHARED_PREERENCE_NAME, MODE_PRIVATE);
    }

    private void getIntentData() {
        Intent intent =getIntent();
        fullName= intent.getStringExtra(AppConstant.FULL_NAME);
        if(TextUtils.isEmpty(fullName)){
            fullName=sharedPreferences.getString(PrefConstant.FULL_NAME, "");
        }
    }

    private void bindView() {
        fabAddNotes=findViewById(R.id.fabAddNotes);
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);



    }

    private void setupDialogBox()
    {
        View view = LayoutInflater.from( MyNotesActivity.this ).inflate( R.layout.add_notes_dialog_layout,null );
        final EditText editTextTitle = view.findViewById( R.id.editTextTitle );
        final EditText editTextDescription = view.findViewById( R.id.editTextDescription );
        Button buttonSubmit = view.findViewById( R.id.buttonSubmit );
        //Dialog to show popup
        final AlertDialog dialog=new AlertDialog.Builder(this).setView(view).setCancelable(false).create();
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title= editTextTitle.getText().toString();
                String description= editTextDescription.getText().toString();
                if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description)) {
                    Notes notes = new Notes();
                    notes.setTitle(title);
                    notes.setDescription(description);
                    notesList.add(notes);
                }
                else
                    Toast.makeText(MyNotesActivity.this, "Title or Description can't be empty", Toast.LENGTH_SHORT).show();
            setupRecyclerView();
                dialog.hide();
            }
        });

        dialog.show();
    }

    private void setupRecyclerView() {
        //INTERFACE
        ItemClickListener itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(Notes notes) {
                Intent intent = new Intent(MyNotesActivity.this, DetailActivity.class);
                intent.putExtra(TITLE, notes.getTitle());
                intent.putExtra(DESCRIPTION, notes.getDescription());


                startActivity(intent);

            }

        };

        NotesAdapter notesAdapter = new NotesAdapter(notesList, itemClickListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyNotesActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewNotes.setLayoutManager(linearLayoutManager);
        recyclerViewNotes.setAdapter(notesAdapter);
    }
}
