package grey.smarthouse.database

import androidx.room.*
import grey.smarthouse.database.converters.IdConverter
import grey.smarthouse.model.Relay
import java.util.*

@Dao
interface RelayDao {
    @Query("SELECT * FROM relayTable")
    fun getAll(): List<Relay>

    @Query("SELECT * FROM relayTable WHERE id = :id")
    fun getById(@TypeConverters(IdConverter::class) id: UUID): Relay

    @Query("SELECT * FROM relayTable WHERE number = :num")
    fun getByNum(@TypeConverters(IdConverter::class) num: Int): Relay

    @Insert
    fun insert(relay: Relay)

    @Update
    fun update(relay: Relay)

    @Delete
    fun delete(relay: Relay)
}

