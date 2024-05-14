package fd.firad.wishwrite.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "room_note")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val title: String,
    val descriptions: String
)
