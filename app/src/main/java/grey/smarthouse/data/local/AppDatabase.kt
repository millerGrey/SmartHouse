package grey.smarthouse.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import grey.smarthouse.data.DataSource
import grey.smarthouse.data.LocalDataSource
import grey.smarthouse.data.Relay
import grey.smarthouse.data.SensorConfig
import grey.smarthouse.data.local.converters.IdConverter
import java.util.*

@Database(entities = [Relay::class], version = 1)
@TypeConverters(IdConverter::class)
abstract class AppDatabase : RoomDatabase(), LocalDataSource {

    abstract fun mRelayDao(): RelayDao


    override fun insert(relay: Relay) {
        mRelayDao().insert(relay)
    }

    override fun update(relay: Relay) {
        mRelayDao().update(relay)
    }

    override suspend fun get(num: Int): Relay {
        return mRelayDao().getByNum(num)
    }

    override suspend fun getAll(): List<Relay> {
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

    override fun delete(relay: Relay) {
        mRelayDao().delete(relay)
    }

}
