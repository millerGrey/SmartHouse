package grey.smarthouse.ui.mainScreen.locations

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import grey.smarthouse.data.Location
import grey.smarthouse.data.LocationWithLists
import grey.smarthouse.data.Repository
import grey.smarthouse.utils.RecyclerViewAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LocationVM (val repository: Repository) : ViewModel() {
    private var _locationsList = MutableLiveData<List<LocationWithLists>>(emptyList())
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

    private fun handleTickEvent() {
        Log.d("RX", "handletick " + Thread.currentThread().name)
        viewModelScope.launch() {
            repository.getAllLocations()?.let {
                _locationsList.value = it
                RVmapFill()
            }
        }
    }

    private fun RVmapFill() {
        RVmap.clear()
        for (index in locationsList.value!!.indices) {
            RVmap.plusAssign(index to RecyclerViewAdapter.LOCATION_LIST_TYPE)
        }
    }

    fun fabCLickListener() {
        _editLocationEvent.value = ""
    }

    fun positiveDialogListener(oldName: String, name: String) {
        viewModelScope.launch {
            val loc = repository.getLocation(oldName)
            loc?.let {
                val location = it
                location.name = name
                repository.update(location)
            } ?: let {
                val location = Location()
                location.name = name
                repository.insert(location)
            }
        }
    }

    fun editLocation(description: String) {
        _editLocationEvent.value = description
    }

    fun deleteLocation(location: Location) {
        repository.delete(location)
    }
}