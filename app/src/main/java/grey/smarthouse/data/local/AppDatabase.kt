package grey.smarthouse.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import grey.smarthouse.data.*
import grey.smarthouse.data.local.converters.IdConverter
import java.util.*

@Database(entities = [Relay::class, Location::class, SensorRoom::class], version = 1)
@TypeConverters(IdConverter::class)
abstract class AppDatabase : RoomDatabase(), DataSource {

    abstract fun mRelayDao(): RelayDao
    abstract fun mSensorDao(): SensorDao
    abstract fun mLocationDao(): LocationDao


    override suspend fun getAllRelays(list: List<Relay>?): List<Relay>? {
        val res =  mRelayDao().getAll()
        if(res.isEmpty()){
            for (i in 0..3) {
                val relay = Relay()
                relay.mode = 2
                relay.number = i + 1
                insert(relay)
            }
            return mRelayDao().getAll()
        }
        return res
    }

    override suspend fun getRelay(num: Int): Relay {
        return mRelayDao().getByNum(num)
    }

    override fun update(relay: Relay) {
        mRelayDao().update(relay)
    }

    override fun insert(relay: Relay) {
        mRelayDao().insert(relay)
    }

    override suspend fun getSensor(num: Int): SensorRoom {
        return mSensorDao().getByNum(num)
    }

    override suspend fun getAllSensors(): List<SensorRoom>? {
        return mSensorDao().getAll()
    }

    override fun update(sensor: SensorRoom) {
        mSensorDao().update(sensor)
    }

    override fun insert(sensor: SensorRoom) {
        mSensorDao().insert(sensor)
    }

    override fun delete(sensor: SensorRoom) {
        mSensorDao().delete(sensor)
    }

    override suspend fun getLocation(name: String): Location {
        TODO("Not yet implemented")
    }

    override suspend fun getAllLocations(): List<LocationWithLists> {
        return mLocationDao().getLocations()
    }

    override fun update(location: Location) {
        TODO("Not yet implemented")
    }

    override fun insert(location: Location) {
        mLocationDao().insert(location)
    }

    override fun delete(location: Location) {
        super.delete(location)
    }
}
