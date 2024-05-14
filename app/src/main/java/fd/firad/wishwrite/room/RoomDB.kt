package fd.firad.wishwrite.room

import androidx.room.Database
import androidx.room.RoomDatabase
import fd.firad.wishwrite.model.Notes

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class RoomDB : RoomDatabase() {
    abstract fun myNotesDAO(): MyDAO
}