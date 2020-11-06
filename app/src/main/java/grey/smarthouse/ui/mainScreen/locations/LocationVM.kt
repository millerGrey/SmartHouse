package grey.smarthouse.ui.mainScreen.locations

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import grey.smarthouse.data.Location
import grey.smarthouse.data.Repository
import grey.smarthouse.data.LocationWithLists
import grey.smarthouse.utils.RecyclerViewAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LocationVM (val repository: Repository) : ViewModel() {
    private var _locationsList = MutableLiveData<List<LocationWithLists>>()
    val locationsList: LiveData<List<LocationWithLists>>
        get() = _locationsList

    private var _editLocationEvent = MutableLiveData<String>()
    val editLocationEvent: LiveData<String>
        get() = _editLocationEvent

    var RVmap: MutableMap<Int, Int> = LinkedHashMap()

    init {
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
            repository.getAllLocations()?.let{
                _locationsList.value = it
                RVmapFill()
            }
        }
    }

    private fun RVmapFill(){
        for (index in locationsList.value!!.indices){
            RVmap.plusAssign(index to RecyclerViewAdapter.LOCATION_LIST_TYPE)
        }
    }

    fun fabCLickListener() {
        _editLocationEvent.value = ""
    }

    fun itemClickListener(name: String) {
        _editLocationEvent.value = name
    }

    fun positiveDialogListener(name: String){
        repository.insert(Location(name))
    }

//    fun update(){
//        viewModelScope.launch {
//            repository.getAllLocations()?.let{
//                _locationsList.value = it
//                RVmapFill()
//            }
//        }
//    }
}