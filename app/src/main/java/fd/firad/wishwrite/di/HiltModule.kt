package fd.firad.wishwrite.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fd.firad.wishwrite.room.MyDAO
import fd.firad.wishwrite.room.RoomDB
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object HiltModule {
    @Singleton
    @Provides
    fun bindMyDao(roomDB: RoomDB): MyDAO = roomDB.myNotesDAO()

    @Provides
    @Singleton
    fun provideDatabaseInstance(@ApplicationContext context: Context): RoomDB =
        Room.databaseBuilder(context, RoomDB::class.java, name = "room_note")
            .allowMainThreadQueries().build()


}