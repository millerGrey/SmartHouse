package grey.smarthouse.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import grey.smarthouse.data.DataSource
import grey.smarthouse.data.Relay
import grey.smarthouse.data.Sensor
import grey.smarthouse.data.SensorConfig
import grey.smarthouse.data.local.converters.IdConverter
import java.util.*

@Database(entities = [Relay::class], version = 1)
@TypeConverters(IdConverter::class)
abstract class AppDatabase : RoomDatabase(), DataSource {
    abstract fun mRelayDao(): RelayDao


    override fun insert(relay: Relay) {
        mRelayDao().insert(relay)
    }

    override fun update(relay: Relay) {
        mRelayDao().update(relay)
    }

    override fun get(id: UUID): Relay {
        return mRelayDao().getById(id)
    }

    override fun get(num: Int): Relay {
        return mRelayDao().getByNum(num)
    }

    override fun getAll(): List<Relay> {
        return mRelayDao().getAll()
    }

    override fun delete(relay: Relay) {
        mRelayDao().delete(relay)
    }

    override fun getSensorList(): List<SensorConfig> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
