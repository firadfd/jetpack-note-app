package com.example.wisewrite.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wisewrite.model.Notes

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class RoomDB : RoomDatabase() {
    abstract fun myNotesDAO(): MyDAO
}