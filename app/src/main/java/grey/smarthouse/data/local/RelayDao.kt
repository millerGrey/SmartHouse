package grey.smarthouse.data.local

import androidx.room.*
import grey.smarthouse.data.DataSource
import grey.smarthouse.data.local.converters.IdConverter
import grey.smarthouse.data.Relay
import java.util.*

@Dao
interface RelayDao{
    @Query("SELECT * FROM relayTable")
    suspend fun getAll(): List<Relay>

    @Query("SELECT * FROM relayTable WHERE number = :num")
    suspend fun getByNum(num: Int): Relay

    @Insert
    fun insert(relay: Relay)

    @Update
    fun update(relay: Relay)

    @Delete
    fun delete(relay: Relay)
}

