package grey.smarthouse.data

import android.util.Log
import kotlinx.coroutines.*
import java.lang.IllegalStateException

import java.util.*
import java.util.concurrent.TimeoutException
import kotlin.coroutines.CoroutineContext

import kotlin.coroutines.suspendCoroutine

class Repository(val local: LocalDataSource, val remote: RemoteDataSource): LocalDataSource, RemoteDataSource {
//    override fun get(id: UUID): Relay {
//        return local.get(id)
//    }

    override suspend fun get(num: Int): Relay {
        var res: Relay = remote.get(num)
        if(res.number > 0)
            return res
        return local.get(num)
    }

    override suspend fun getAll(): List<Relay> {
        lateinit var res: List<Relay>

        withTimeoutOrNull(2000) {
            res = remote.getAll()
        }?:let{
            res = local.getAll()
        }
        return res
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

    override fun getRelayStates(): List<String> {
        return remote.getRelayStates()
    }

    override fun setRelayState(num: Int, enable: Boolean) {
        remote.setRelayState(num, enable)
    }
}