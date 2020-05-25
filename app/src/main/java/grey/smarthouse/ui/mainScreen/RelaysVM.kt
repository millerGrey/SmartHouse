package grey.smarthouse.ui.mainScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import grey.smarthouse.data.DataSource
import grey.smarthouse.data.Relay
import grey.smarthouse.data.remote.Requests
import grey.smarthouse.utils.RecyclerViewAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.LinkedHashMap


class RelaysVM(val repository: DataSource) : ViewModel() {

    private var _openRelayEvent = MutableLiveData<UUID?>(null)
    val openRelayEvent: LiveData<UUID?>
        get() = _openRelayEvent

    private var _relayList = MutableLiveData<List<Relay>>(emptyList())

    val relayList: LiveData<List<Relay>>
        get() = _relayList

    private var _relayValueList = MutableLiveData(listOf("0", "0", "0", "0"))

    val relayValueList: LiveData<List<String>>
        get() = _relayValueList

    var RVmap: MutableMap<Int, Int> = LinkedHashMap()

    var refresh: Disposable
    init{
        _relayList.value = repository.getAll()
        RVmapFill()
        refresh = Observable.interval(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .doOnNext{_-> handleTickEvent() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({},
                { e ->
                        Log.d("ALL ESCAPE", "complete")
                        e.printStackTrace()
                    },
                { Log.d("RX", "complete") },
                { _ -> Log.d("RX", "sub") })
    }

    fun handleTickEvent(){
        val r = Requests.relayStateRequest().toMutableList()
        Log.d("RX Relay", Thread.currentThread().name)
        if(r.isNotEmpty()){
            _relayValueList.postValue(r)
        }
    }

    fun RVmapFill(){
        for (index in relayList.value!!.indices){
            RVmap.plusAssign(index to RecyclerViewAdapter.RELAY_LIST_TYPE)
        }
    }

    fun relayToggle(relay: Relay){
        if(relayValueList.value?.get((relay.number - 1)) == "1")
            Requests.relayOffRequest(relay.number)
        else
            Requests.relayOnRequest(relay.number)
    }


    fun openRelay(id: UUID){
        _openRelayEvent.value = id
        _openRelayEvent.value = null
    }

    fun updateConfig(){
        val relays = relayList.value
        relays?.let {
            for (r in it) {
                repository.get(r.number)
            }
        }
    }

}