package grey.smarthouse.data

import java.util.*

class Repository(val local: DataSource, val remote: DataSource): DataSource {
    override fun get(id: UUID): Relay {
        return local.get(id)
    }

    override fun get(num: Int): Relay {

        return local.get(num)
    }

    override fun getAll(): List<Relay> {
        return local.getAll()
    }

    override fun insert(relay: Relay) {
        local.insert(relay)
    }

    override fun update(relay: Relay) {
        local.update(relay)
        remote.update(relay)
    }

    override fun delete(relay: Relay) {
        local.delete(relay)
    }

    override fun getSensorList(): List<SensorConfig> {
        return remote.getSensorList()
    }
}