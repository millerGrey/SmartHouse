package grey.smarthouse.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import java.util.List;
import java.util.UUID;

import grey.smarthouse.model.Relay;
import grey.smarthouse.model.Repository;

@Database(entities = {Relay.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase implements Repository {
    public abstract RelayDao mRelayDao();


    @Override
    public void insert(Relay relay) {
        mRelayDao().insert(relay);
    }

    @Override
    public void update(Relay relay) {
        mRelayDao().update(relay);
    }

    @Override
    public Relay get(UUID id) {
        return mRelayDao().getById(id);
    }

    @Override
    public List<Relay> getAll() {
        return mRelayDao().getAll();
    }

    @Override
    public void delete(Relay relay) {
        mRelayDao().delete(relay);
    }
}
