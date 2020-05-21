package grey.smarthouse.model

import grey.smarthouse.data.Sensor
import grey.smarthouse.data.DataSource
import grey.smarthouse.data.Relay
import java.util.*

/**
 * Created by GREY on 30.04.2018.
 */

class RelayList private constructor() {
    private var mRelays: List<Relay>
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

    fun getRelay(num: Int): Relay {
        return mRepo!!.get(num)
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

        private var mRepo: DataSource? = null

        val instance: RelayList
            get() {
                if (sRelayList == null) {
                    sRelayList = RelayList()
                }
                return sRelayList as RelayList
            }

        fun setRepo(repo: DataSource) {
            mRepo = repo
        }
    }
}
