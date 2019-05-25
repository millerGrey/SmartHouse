package grey.smarthouse.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

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

