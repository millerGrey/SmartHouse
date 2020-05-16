package grey.smarthouse.model

import java.util.*


interface Repository {

    fun get(id: UUID): Relay

    fun getAll(): List<Relay>

    fun insert(relay: Relay)

    fun update(relay: Relay)

    fun delete(relay: Relay)
}
