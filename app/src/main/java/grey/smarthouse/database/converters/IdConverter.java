package grey.smarthouse.database.converters;

import androidx.room.TypeConverter;

import java.util.UUID;

public class IdConverter {

    @TypeConverter
    public String idToString(UUID id) {
        if (id == null) {
            return null;
        } else {
            return id.toString();
        }
    }
    @TypeConverter
    public UUID stringToId(String string){
        return UUID.fromString(string);
    }

}
