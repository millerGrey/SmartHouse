package grey.smarthouse.database.converters

import androidx.room.TypeConverter
import java.util.*

class IdConverter {

    @TypeConverter
    fun idToString(id: UUID?): String? {
        return id?.toString()
    }

    @TypeConverter
    fun stringToId(string: String): UUID {
        return UUID.fromString(string)
    }

}
