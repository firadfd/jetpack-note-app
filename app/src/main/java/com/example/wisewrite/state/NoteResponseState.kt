package com.example.wisewrite.state

sealed class NoteResponseState<out T> {
    data class Success<out T>(val data: T) : NoteResponseState<T>()
    data class Error(val errorMessage: String) : NoteResponseState<Nothing>()
    object Loading : NoteResponseState<Nothing>()
}
