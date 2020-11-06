package grey.smarthouse.data.local

import androidx.room.*
import grey.smarthouse.data.Relay
import grey.smarthouse.data.SensorRoom

@Dao
interface SensorDao {
    @Query("SELECT * FROM sensorTable")
    suspend fun getAll(): List<SensorRoom>

    @Query("SELECT * FROM sensorTable WHERE number = :num")
    suspend fun getByNum(num: Int): SensorRoom

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sensor: SensorRoom)

    @Update
    fun update(sensor: SensorRoom)

    @Delete
    fun delete(sensor: SensorRoom)
}