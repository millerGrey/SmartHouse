package grey.smarthouse.data

import java.util.*


interface DataSource {

    suspend fun getRelay(num: Int): Relay
    //TODO divide responsibilities. backend realisation
    suspend fun getAllRelays(list: List<Relay>? = null): List<Relay>?

    fun update(relay: Relay)

    fun insert(relay: Relay){}

    suspend fun getSensor(num: Int): SensorRoom
    //TODO divide responsibilities. backend realisation
    suspend fun getAllSensors(): List<SensorRoom>?

    fun update(sensor: SensorRoom)

    fun insert(sensor: SensorRoom){}

    fun delete(sensor: SensorRoom){}

    suspend fun getLocation(name: String): Location

    suspend fun getAllLocations(): List<LocationWithLists>

    fun update(location: Location)

    fun insert(location: Location){}

    fun delete(location: Location){}
}

