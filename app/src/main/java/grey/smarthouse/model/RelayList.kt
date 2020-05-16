package grey.smarthouse.model

import java.util.*

/**
 * Created by GREY on 30.04.2018.
 */

class RelayList private constructor() {
    internal var mRelays: List<Relay>
    internal var mSensors = emptyList<Sensor>()

    val relays: List<Relay>
        get() = mRepo!!.getAll()

    init {

        mRelays = relays
        if (mRelays.size == 0) {
            for (i in 0..3) {
                val relay = Relay()
                relay.mode = 2
                relay.number = i + 1
                addRelay(relay)
            }
        }
        mRelayStates = ArrayList()
        for (i in 0..4) {
            mRelayStates.add(i, "OFF")
        }
    }

    fun getRelay(id: UUID): Relay {
        return mRepo!!.get(id)
    }

    fun updateRelay(relay: Relay) {
        mRepo!!.update(relay)
    }

    fun addRelay(relay: Relay) {
        mRepo!!.insert(relay)
    }

    companion object {
        private var sRelayList: RelayList? = null
        lateinit var mRelayStates: MutableList<String>

        private var mRepo: Repository? = null

        val instance: RelayList
            get() {
                if (sRelayList == null) {
                    sRelayList = RelayList()
                }
                return sRelayList as RelayList
            }

        fun setRepo(repo: Repository) {
            mRepo = repo
        }
    }
}
