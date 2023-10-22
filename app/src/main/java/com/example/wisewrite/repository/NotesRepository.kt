package com.example.wisewrite.repository

import com.example.wisewrite.model.Notes
import com.example.wisewrite.room.MyDAO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotesRepository @Inject constructor(private val dao: MyDAO) {

    fun getAllNotes(): Flow<List<Notes>> = dao.getNote()


    fun insertNotes(notes: Notes) {
        dao.insert(notes)
    }

    fun deleteNotes(notes: Notes) {
        dao.deleteNote(notes)
    }

    fun updateNotes(notes: Notes) {
        dao.updateNote(notes)
    }
}