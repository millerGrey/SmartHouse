package grey.smarthouse.data

import android.util.Log
import kotlinx.coroutines.withTimeoutOrNull

class Repository(private val local: DataSource, private val remote: DataSource): DataSource {
    private final val TAG = "Repo"
    override suspend fun getRelay(num: Int): Relay {
        val relay: Relay = remote.getRelay(num)
        if (relay.number > 0) {
            local.update(relay)
            return relay
        }
        return local.getRelay(num)
    }
//?
    override suspend fun getAllRelays(l: List<Relay>?): List<Relay>? {
        Log.d(TAG,"start response relays")
        var list: List<Relay>? = null
        list = local.getAllRelays()
        withTimeoutOrNull(2000) {

            remote.getAllRelays()?.let{
                list = it
                if(it.isNotEmpty()){
                    list?.forEach {
                        sensor -> local.update(sensor)
                    }
                }
            }
        } ?: let {
//            list = local.getAllRelays()
        }
        Log.d(TAG,"end response relays")
        return list
    }

    override fun insert(relay: Relay) {
        local.insert(relay)
    }

    //?
    override suspend fun update(relay: Relay) {
        remote.update(relay)
        local.update(relay)
    }

    override suspend fun getSensor(num: Int): Sensor {
        TODO("Not yet implemented")
    }
//?

    override suspend fun getAllSensors(): List<Sensor>? {
        var list: List<Sensor>? = null
        withTimeoutOrNull(2000) {
            list = remote.getAllSensors()
            list?.let {
                if (it.isNotEmpty()) {
                    list?.forEach { sensor ->
                        local.insert(sensor)
                    }
                }
            }
        } ?: let {
//            list = local.getAllSensors()
        }
        return list
    }

    override fun update(sensor: Sensor) {
        remote.update(sensor)
    }

    override fun insert(sensor: Sensor) {
        local.insert(sensor)
    }

    override fun delete(sensor: Sensor) {
        local.delete(sensor)
    }

    override suspend fun getLocation(name: String): Location? {
        return local.getLocation(name)
    }

    override suspend fun getLocation(id: Int): Location? {
        return local.getLocation(id)
    }

    override suspend fun getAllLocations(): List<LocationWithLists> {
        return local.getAllLocations()
    }

    override fun update(location: Location) {
        local.update(location)
    }

    override fun insert(location: Location) {
        local.insert(location)
    }

    override fun delete(location: Location) {
        local.delete(location)
    }
}