package grey.smarthouse.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import grey.smarthouse.data.*
import grey.smarthouse.data.local.converters.IdConverter

@Database(entities = [Relay::class, Location::class, Sensor::class], version = 1)
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

    override suspend fun update(relay: Relay) {
        mRelayDao().update(relay)
    }

    override fun insert(relay: Relay) {
        mRelayDao().insert(relay)
    }

    override suspend fun getSensor(num: Int): Sensor {
        return mSensorDao().getByNum(num)
    }

    override suspend fun getAllSensors(): List<Sensor>? {
        return mSensorDao().getAll()
    }

    override suspend fun update(sensor: Sensor) {
        mSensorDao().update(sensor)
    }

    override fun insert(sensor: Sensor) {
        mSensorDao().insert(sensor)
    }

    override fun delete(sensor: Sensor) {
        mSensorDao().delete(sensor)
    }

    override suspend fun getLocation(name: String): Location? {
        return mLocationDao().getLocation(name)
    }

    override suspend fun getLocation(id: Int): Location? {
        return mLocationDao().getLocation(id)
    }

    override suspend fun getAllLocations(): List<LocationWithLists> {
        return mLocationDao().getLocations()
    }

    override fun update(location: Location) {
        mLocationDao().update(location)
    }

    override fun insert(location: Location) {
        mLocationDao().insert(location)
    }

    override fun delete(location: Location) {
        mLocationDao().delete(location)
    }
}
