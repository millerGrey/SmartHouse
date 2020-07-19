package grey.smarthouse.data

import android.util.Log
import kotlinx.coroutines.*

import java.util.*
import java.util.concurrent.TimeoutException

class Repository(val local: DataSource, val remote: DataSource): DataSource {
//    override fun get(id: UUID): Relay {
//        return local.get(id)
//    }

    override fun get(num: Int): Relay {
        lateinit var res: Relay
        runBlocking(Dispatchers.IO) {
            withTimeoutOrNull(2000) {
                res = remote.get(num)
            }
        }
        if(res.number > 0)
            return res
        return local.get(num)
    }

    override fun getAll(): List<Relay> {
        lateinit var res: List<Relay>
        runBlocking(Dispatchers.IO) {
            Log.d("COR", "start")
            withTimeoutOrNull(2000) {
                res = remote.getAll()
            }
        }

        for(r in res){
            if(r.number > 0) {
                local.update(r)
            }else{
                return local.getAll()
            }
        }
        Log.d("COR", "end")
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
}