package grey.smarthouse.data.local

import androidx.room.*
import grey.smarthouse.data.Relay

@Dao
interface RelayDao{
    @Query("SELECT * FROM relayTable")
    suspend fun getAll(): List<Relay>

    @Query("SELECT * FROM relayTable WHERE number = :num")
    suspend fun getByNum(num: Int): Relay

    @Insert
    fun insert(relay: Relay)

    @Update
    suspend fun update(relay: Relay)

    @Delete
    fun delete(relay: Relay)
}

