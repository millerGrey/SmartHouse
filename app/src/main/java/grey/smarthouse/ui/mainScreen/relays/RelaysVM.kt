package grey.smarthouse.ui.mainScreen.relays

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import grey.smarthouse.data.Relay
import grey.smarthouse.data.Repository
import grey.smarthouse.utils.RecyclerViewAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class RelaysVM(val repository: Repository) : ViewModel() {
    private val TAG = "relaysVM"
    private var _openRelayEvent = MutableLiveData<Int?>(null)
    val openRelayEvent: LiveData<Int?>
        get() = _openRelayEvent

    private var _relayList = MutableLiveData<List<Relay>>(emptyList())
    val relayList: LiveData<List<Relay>>
        get() = _relayList

    var RVmap: MutableMap<Int, Int> = LinkedHashMap()

    init{

        viewModelScope.launch {
            while (true) {
                handleTickEvent()
                delay(2000)
            }
        }
    }

    private fun handleTickEvent(){
        Log.d("RX","handletick "+ Thread.currentThread().name)
        viewModelScope.launch() {
            Log.d(TAG,"start coroutine states "+ Thread.currentThread().name)
            repository.getAllRelays()?.let{
                _relayList.value = it
                RVmapFill()
            }
            Log.d(TAG,"end coroutine states")
        }
    }

    private fun RVmapFill(){
        for (index in relayList.value!!.indices){
            RVmap.plusAssign(index to RecyclerViewAdapter.RELAY_LIST_TYPE)
        }
    }

    fun relayToggle(relay: Relay) {
        relay.state = !relay.state
        viewModelScope.launch() {
            repository.update(relay)
        }

    }


    fun openRelay(num: Int) {
        _openRelayEvent.value = num
        _openRelayEvent.value = null
    }
}