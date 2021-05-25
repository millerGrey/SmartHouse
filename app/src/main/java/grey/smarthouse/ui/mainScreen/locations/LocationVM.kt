package grey.smarthouse.ui.mainScreen.locations

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import grey.smarthouse.App
import grey.smarthouse.R
import grey.smarthouse.data.Location
import grey.smarthouse.data.LocationWithLists
import grey.smarthouse.data.Repository
import grey.smarthouse.utils.RecyclerViewAdapter
import grey.smarthouse.utils.SingleLiveEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LocationVM(val application: App, val repository: Repository) : AndroidViewModel(application) {
    private var _locationsList = MutableLiveData<List<LocationWithLists>>(emptyList())
    val locationsList: LiveData<List<LocationWithLists>>
        get() = _locationsList

    private var _editLocationEvent = SingleLiveEvent<Int?>()
    val editLocationEvent: LiveData<Int?>
        get() = _editLocationEvent

    private var _dialogDismiss = SingleLiveEvent<Boolean>()
    val dialogDismiss: LiveData<Boolean?>
        get() = _dialogDismiss

    val name: MutableLiveData<String> = MutableLiveData()
    var error: MutableLiveData<String> = MutableLiveData()


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
        _editLocationEvent.value = -1
    }

    fun saveLocation() {
        viewModelScope.launch {
            val loc = repository.getLocation(name.value!!)
            loc?.let {
                if (loc.id != editLocationEvent.value) {
                    error.value = application.resources.getString(R.string.locationNameDublicateError)
                } else {
                    _dialogDismiss.value = true
                }
            } ?: let {
                val location = Location()
                location.name = name.value!!
                if (name.value!!.isEmpty()) {
                    error.value = application.resources.getString(R.string.locationNameEmptyError)
                } else if (editLocationEvent.value!! > 0) {
                    location.id = editLocationEvent.value!!
                    repository.update(location)
                    _dialogDismiss.value = true
                } else {
                    repository.insert(location)
                    _dialogDismiss.value = true
                }
            }
        }
    }

    fun dismissListener() {
        error.value = null
    }

    fun editLocation(id: Int) {
        _editLocationEvent.value = id
    }

    fun deleteLocation(location: Location) {
        repository.delete(location)
    }

    fun fillDescription(id: Int) {
        viewModelScope.launch {
            if (id > 0) {
                val loc = repository.getLocation(id)
                name.value = loc?.name
                Log.d("TAG", "$loc, ${name.value}")
            } else {
                name.value = ""
            }
        }
    }
}