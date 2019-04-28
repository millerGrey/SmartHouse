package grey.smarthouse.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;

import java.util.UUID;

import grey.smarthouse.model.Relay;

@Database(entities = {Relay.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RelayDao mRelayDao();

    public static class IdConverter {

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
}
