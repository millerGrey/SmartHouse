package grey.smarthouse.data


interface DataSource {

    suspend fun getRelay(num: Int): Relay
    //TODO divide responsibilities. backend realisation
    suspend fun getAllRelays(list: List<Relay>? = null): List<Relay>?

    suspend fun update(relay: Relay)

    fun insert(relay: Relay){}

    suspend fun getSensor(num: Int): Sensor

    //TODO divide responsibilities. backend realisation
    suspend fun getAllSensors(): List<Sensor>?

    fun update(sensor: Sensor)

    fun insert(sensor: Sensor) {}

    fun delete(sensor: Sensor) {}

    suspend fun getLocation(name: String): Location

    suspend fun getAllLocations(): List<LocationWithLists>

    fun update(location: Location)

    fun insert(location: Location){}

    fun delete(location: Location){}
}

