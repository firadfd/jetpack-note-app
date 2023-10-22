package com.example.wisewrite.room

import androidx.room.*
import com.example.wisewrite.model.Notes
import kotlinx.coroutines.flow.Flow

@Dao
interface MyDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(notes: Notes)

    @Query("SELECT * FROM room_note")
    fun getNote(): Flow<List<Notes>>

    @Delete
    fun deleteNote(notes: Notes)

    @Update
    fun updateNote(notes: Notes)
}