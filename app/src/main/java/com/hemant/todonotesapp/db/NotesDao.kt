package com.hemant.todonotesapp.db

//dao---> Data Access Objects
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
//interface to communicate databse with view
@Dao
interface NotesDao{
    @Query("SELECT * FROM notesData")
    fun getAll():List<Notes>
    @Insert(onConflict = REPLACE)
    fun insert(notes:Notes)
    @Update
    fun updateNotes(notes: Notes?)
    @Delete
    fun delete(notes:Notes)
    @Query("DELETE FROM notesData WHERE isTaskCompleted = :status")
    fun deleteNotes(status : Boolean)
}
