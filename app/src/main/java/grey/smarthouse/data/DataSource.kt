package grey.smarthouse.data

import java.util.*


interface DataSource {

    fun get(id: UUID): Relay

    fun get(num: Int): Relay

    fun getAll(): List<Relay>

    fun insert(relay: Relay)

    fun update(relay: Relay)

    fun delete(relay: Relay)

    fun getSensorList(): List<SensorConfig>

}
