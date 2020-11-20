package grey.smarthouse.data.local

import androidx.room.*
import grey.smarthouse.data.Sensor

@Dao
interface SensorDao {
    @Query("SELECT * FROM sensorTable")
    suspend fun getAll(): List<Sensor>

    @Query("SELECT * FROM sensorTable WHERE number = :num")
    suspend fun getByNum(num: Int): Sensor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sensor: Sensor)

    @Update
    fun update(sensor: Sensor)

    @Delete
    fun delete(sensor: Sensor)
}