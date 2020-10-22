package grey.smarthouse.data

import java.util.*


interface DataSource {

    suspend fun get(num: Int): Relay

    suspend fun getAll(): List<Relay>

    fun update(relay: Relay)
}
