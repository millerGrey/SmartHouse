package grey.smarthouse.ui.mainScreen.sensors

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import grey.smarthouse.data.Repository
import grey.smarthouse.data.Sensor
import grey.smarthouse.utils.RecyclerViewAdapter
import grey.smarthouse.utils.SingleLiveEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SensorsVM(val repository: Repository) : ViewModel() {
    private val TAG = "sensorsVM"

    private var _sensorsList = MutableLiveData<List<Sensor>>(emptyList())
    val sensorsList: LiveData<List<Sensor>>
        get() = _sensorsList

    private var _progress = MutableLiveData<Boolean>(true)
    val progress: LiveData<Boolean>
        get() = _progress

    private var _cnt = MutableLiveData<Int>(0)
    val cnt: LiveData<Int>
        get() = _cnt

    private var _editSensorEvent = MutableLiveData<Int>()
    val editSensorEvent: LiveData<Int>
        get() = _editSensorEvent

    private var _dialogDismiss = SingleLiveEvent<Boolean>()
    val dialogDismiss: LiveData<Boolean?>
        get() = _dialogDismiss

    val sensorDescription: MutableLiveData<String> = MutableLiveData()
    val locationList: MutableLiveData<MutableList<String>> = MutableLiveData()
    var selectedPosition: MutableLiveData<Int> = MutableLiveData(0)

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

    private fun RVmapFill() {
        for (index in sensorsList.value!!.indices) {
            RVmap.plusAssign(index to RecyclerViewAdapter.SENSOR_LIST_TYPE)
        }
    }

    private suspend fun handleTickEvent() {
        Log.d(TAG, "start coroutine " + Thread.currentThread().name)
        repository.getAllSensors()?.let{
            _sensorsList.value = it
            if(it.isNotEmpty()){
                RVmapFill()
                _progress.value = false
            } else {
                _progress.value = true
                _cnt.postValue(cnt.value?.plus(1))
                return
            }
            _cnt.postValue(0)
        }
        Log.d(TAG, "end coroutine")
    }

    fun editSensorDescription(num: Int) {
        _editSensorEvent.value = num
    }

    fun fillDescription(num: Int) {
        error.value = null
        viewModelScope.launch {
            if (num >= 0) {
                val sensor = repository.getSensor(num)
                locationList.value = repository.getAllLocations().map { it.location.name }.toMutableList().apply { add(0, "---") }
                sensorDescription.value = sensor.description
                Log.d(TAG, "$sensor, ${sensorDescription.value}")
                locationList.value?.let {
                    val index = it.indexOf(sensor.location)
                    selectedPosition.value = if (index > 0) index else 0
                }
            }
        }
    }

    fun saveSensor() {
        viewModelScope.launch {
            val sensor = repository.getSensor(_editSensorEvent.value!!)
            sensor.description = sensorDescription.value!!
            if (selectedPosition.value!! > 0)
                sensor.location = locationList.value?.get(selectedPosition.value!!).toString()
            repository.update(sensor)
            _dialogDismiss.value = true
        }
    }

    fun dismissListener() {
        error.value = null
    }
}