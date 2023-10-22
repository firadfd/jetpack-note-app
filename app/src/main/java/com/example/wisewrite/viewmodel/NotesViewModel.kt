package com.example.wisewrite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wisewrite.model.Notes
import com.example.wisewrite.repository.NotesRepository
import com.example.wisewrite.state.NoteResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val repository: NotesRepository) : ViewModel() {

    private val _notes =
        MutableStateFlow<NoteResponseState<List<Notes>>>(NoteResponseState.Loading)

    val note: StateFlow<NoteResponseState<List<Notes>>> = _notes

    fun addNotes(notes: Notes) {
        repository.insertNotes(notes)
    }

    fun deleteNotes(notes: Notes) {
        repository.deleteNotes(notes)
    }

    fun updateNotes(notes: Notes) {
        repository.updateNotes(notes)
    }

    init {
        viewModelScope.launch {
            getAllNote()
        }
    }

    private suspend fun getAllNote() {
        repository.getAllNotes().collect { notes ->
            _notes.value = NoteResponseState.Success(notes)
        }
    }


}