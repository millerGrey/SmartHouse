package grey.smarthouse.data.local

import androidx.room.*
import grey.smarthouse.data.Location
import grey.smarthouse.data.LocationWithLists


@Dao
interface LocationDao {

    @Query("SELECT * FROM locationTable WHERE name = :name")
    suspend fun getLocation(name: String): Location

    @Transaction
    @Query("SELECT * FROM locationTable")
    suspend fun getLocations(): List<LocationWithLists>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(location: Location)

    @Update
    fun update(location: Location)

    @Delete
    fun delete(location: Location)



}