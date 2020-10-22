package grey.smarthouse.ui.mainScreen.relays

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.*
import grey.smarthouse.data.Relay
import grey.smarthouse.data.Repository
import grey.smarthouse.utils.RecyclerViewAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlin.collections.LinkedHashMap


class RelaysVM(val repository: Repository) : ViewModel() {

    private var _openRelayEvent = MutableLiveData<Int?>(null)
    val openRelayEvent: LiveData<Int?>
        get() = _openRelayEvent

    private var _relayList = MutableLiveData<List<Relay>>(emptyList())
    val relayList: LiveData<List<Relay>>
        get() = _relayList

    private var _relayValueList = MutableLiveData(listOf("0", "0", "0", "0"))
    val relayValueList: LiveData<List<String>>
        get() = _relayValueList


    var relays = MediatorLiveData<Int>()

    var RVmap: MutableMap<Int, Int> = LinkedHashMap()

    var refresh: Disposable
    init{
        relays.addSource(_relayList){
            relays.value = 1
        }
        relays.addSource(_relayValueList){
            relays.value = 1}

        viewModelScope.launch() {
            try {
                _relayList.value = repository.getAll()//listOf(Requests.get(1),Requests.get(2),Requests.get(3),Requests.get(4))
            } catch (e: Exception) {
                Log.d("TCP","EX")
            }
            RVmapFill()
        }

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
        val r = repository.getRelayStates().toMutableList()
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
            repository.setRelayState(relay.number, false)
        else
            repository.setRelayState(relay.number, true)
    }


    fun openRelay(num: Int){
        _openRelayEvent.value = num
        _openRelayEvent.value = null
    }

    fun updateConfig(){
        val relays = relayList.value
//        relays?.let {
//            for (r in it) {
//                _relayList.postValue(repository.getAll())
//            }
//        }
    }

}