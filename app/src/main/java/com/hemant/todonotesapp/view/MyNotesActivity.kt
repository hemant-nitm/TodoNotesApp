package com.hemant.todonotesapp.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hemant.todonotesapp.NotesApp
import com.hemant.todonotesapp.R
import com.hemant.todonotesapp.adapter.NotesAdapter
import com.hemant.todonotesapp.clicklisteners.ItemClickListener
import com.hemant.todonotesapp.db.Notes
import com.hemant.todonotesapp.utils.AppConstant
import com.hemant.todonotesapp.utils.PrefConstant
import com.hemant.todonotesapp.workmanager.MyWorker
import java.util.*
import java.util.concurrent.TimeUnit

class  MyNotesActivity : AppCompatActivity() {
    var fullName: String?=null
    lateinit var fabAddNotes: FloatingActionButton
    lateinit var recyclerViewNotes: RecyclerView
    val ADD_NOTES_CODE=100
    var notesList = ArrayList<Notes>()
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_notes)
        bindView()
        setupSharedPreference()
        getIntentData()
        getDataFromDb()
        setUpRecyclerView()
      setUpWorkManager()
        supportActionBar?.title = fullName
        fabAddNotes.setOnClickListener (object:View.OnClickListener{
            override fun onClick(v: View?) {
                //setUpDialogBox()
                val intent=Intent(this@MyNotesActivity,AddNotesActivity::class.java)
                startActivityForResult(intent,ADD_NOTES_CODE)
            }
        })
    }

    private fun setUpWorkManager() {
        val constraint= Constraints.Builder().build()
        val workRequest= PeriodicWorkRequest //for only 1 onetimeWorkRequest
                .Builder(MyWorker::class.java,15, TimeUnit.MINUTES)
                .setConstraints(constraint)
                .build()
        WorkManager.getInstance().enqueue(workRequest)
    }
    private fun setupSharedPreference() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    private fun getIntentData() {
        val intent = intent
        if(intent.hasExtra(AppConstant.FULL_NAME)){
            fullName = intent.getStringExtra(AppConstant.FULL_NAME)
        }
        if (fullName==null) {
            fullName = sharedPreferences.getString(PrefConstant.FULL_NAME," ")?:""
        }

    }

    private fun bindView() {
        fabAddNotes = findViewById(R.id.fabAddNotes)
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes)
    }

    @SuppressLint("InflateParams")
    private fun setUpDialogBox() {
        val view = LayoutInflater.from(this@MyNotesActivity).inflate(R.layout.add_notes_dialog_layout, null)
        val editTextTitle = view.findViewById<EditText>(R.id.EditTextTitle)
        val editTextDescription = view.findViewById<EditText>(R.id.EditTextDescription)
        val buttonSubmit = view.findViewById<Button>(R.id.buttonSubmit)
        val dialog = AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create()
        buttonSubmit.setOnClickListener (object:View.OnClickListener{  //upto here
            override fun onClick(p0: View?) {
                val title = editTextTitle.text.toString()
                val description = editTextDescription.text.toString()
                if (title.isNotEmpty() && description.isNotEmpty()) {
                    //this error is coming because in db->notes there are 5 column,
                    // so we have to pass it specifically,rest 3 are by default value
//                    val notes = Notes(title,description)
                    val notes = Notes(title=title,description=description)
                    //adding into recyclerview
                    notesList.add(notes)
                    addNotesToDb(notes)
                } else {
                    Toast.makeText(this@MyNotesActivity, "Title and Description can't be empty", Toast.LENGTH_SHORT).show()
                }
//                setUpRecyclerView()
                dialog.hide()
            }
        })
        dialog.show()
    }
    private fun getDataFromDb(){
        val notesApp=applicationContext as NotesApp
        val notesDao=notesApp.getNotesDb().notesDao()
        val noteList=notesDao.getAll()
        notesList.addAll(noteList)

    }

    private fun addNotesToDb(notes: Notes) {
        //insert into Db
        val notesApp=applicationContext as NotesApp
        val notesDao=notesApp.getNotesDb().notesDao()
        notesDao.insert(notes)

    }

    private fun setUpRecyclerView() {
        val itemClickListener= object : ItemClickListener {
            override fun onClick(notes: Notes?) {
                val intent = Intent(this@MyNotesActivity, DetailActivity::class.java)
                intent.putExtra(AppConstant.TITLE, notes?.title)
                intent.putExtra(AppConstant.DESCRIPTION, notes?.description)
                startActivity(intent)
            }

            override fun onUpdate(notes: Notes?) {
                //update the value of checkbox
                val notesApp=applicationContext as NotesApp
                val notesDao=notesApp.getNotesDb().notesDao()
                notesDao.updateNotes(notes)
            }
        }
        val notesAdapter = NotesAdapter(notesList, itemClickListener)
        val linearLayoutManager = LinearLayoutManager(this@MyNotesActivity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL //setting the item is shown horizontal  or veritical
        recyclerViewNotes.layoutManager = linearLayoutManager
        recyclerViewNotes.adapter = notesAdapter
    }
//29.55sec perm cont glide
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==ADD_NOTES_CODE && resultCode== Activity.RESULT_OK){
            val title=data?.getStringExtra(AppConstant.TITLE)
            val description=data?.getStringExtra(AppConstant.DESCRIPTION)
            val imagePath=data?.getStringExtra(AppConstant.IMAGE_PATH)
            //now we have to add into db
            val notes=Notes(title=title!!,description = description!!,imagePath = imagePath!!,isTaskCompleted = false)
            addNotesToDb(notes)
            notesList.add(notes)
            recyclerViewNotes.adapter?.notifyItemChanged(notesList.size-1)
        }

    }
    //implemented menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        return super.onCreateOptionsMenu(menu)
        val inflater=menuInflater
        inflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId==R.id.blog){
            //we want to open a blog activity when clicked on the item
            Log.d("MyNotesActivity","blog clicked")
            val intent=Intent(this@MyNotesActivity,BlogActivity::class.java)
            startActivity(intent)

        }

        return super.onOptionsItemSelected(item)
    }
}