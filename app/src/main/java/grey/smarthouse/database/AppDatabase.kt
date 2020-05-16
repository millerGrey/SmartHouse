package grey.smarthouse.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import grey.smarthouse.database.converters.IdConverter
import grey.smarthouse.model.Relay
import grey.smarthouse.model.Repository
import java.util.*

@Database(entities = [Relay::class], version = 1)
@TypeConverters(IdConverter::class)
abstract class AppDatabase : RoomDatabase(), Repository {
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

    override fun getAll(): List<Relay> {
        return mRelayDao().getAll()
    }

    override fun delete(relay: Relay) {
        mRelayDao().delete(relay)
    }
}
