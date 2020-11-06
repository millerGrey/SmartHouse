package grey.smarthouse.ui.mainScreen.sensors

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import grey.smarthouse.data.Repository
import grey.smarthouse.data.SensorRoom
import grey.smarthouse.utils.RecyclerViewAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class SensorsVM(val repository: Repository) : ViewModel() {
    private val TAG = "sensorsVM"

    private var _sensorsList = MutableLiveData<List<SensorRoom>>(emptyList())
    val sensorsList: LiveData<List<SensorRoom>>
        get() = _sensorsList

    private var _progress = MutableLiveData<Boolean>(true)
    val progress: LiveData<Boolean>
        get() = _progress

    private var _cnt = MutableLiveData<Int>(0)
    val cnt: LiveData<Int>
        get() = _cnt

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
            }else {
                _progress.value = true
                _cnt.postValue(cnt.value?.plus(1))
                return
            }
            _cnt.postValue(0)
        }
        Log.d(TAG, "end coroutine")


    }
}