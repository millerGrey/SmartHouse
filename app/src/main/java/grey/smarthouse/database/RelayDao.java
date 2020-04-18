package grey.smarthouse.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import java.util.List;
import java.util.UUID;

import grey.smarthouse.model.Relay;
import grey.smarthouse.database.converters.*;

@Dao
public interface RelayDao {
    @Query("SELECT * FROM relayTable")
    List<Relay> getAll();

    @Query("SELECT * FROM relayTable WHERE mId = :id")
    Relay getById(@TypeConverters({IdConverter.class}) UUID id);

    @Insert
    void insert(Relay relay);

    @Update
    void update(Relay relay);

    @Delete
    void delete(Relay relay);
}

