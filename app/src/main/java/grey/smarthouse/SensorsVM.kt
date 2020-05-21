package grey.smarthouse

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import grey.smarthouse.data.Sensor
import grey.smarthouse.data.SensorConfig
import grey.smarthouse.data.remote.Requests
import grey.smarthouse.services.NetService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class SensorsVM(): ViewModel(){

    private var _sensorsList = MutableLiveData<List<String>>(emptyList())
    val sensorsList: LiveData<List<String>>
        get() = _sensorsList

    private var _progress = MutableLiveData<Boolean>(true)
    val progress: LiveData<Boolean>
        get() = _progress

    private var _cnt = MutableLiveData<Int>(0)
    val cnt: LiveData<Int>
        get() = _cnt

    var sensorsConfig: List<SensorConfig> = emptyList()


    var refresh: Observable<Long>
    init{
        refresh = Observable.interval(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        refresh.subscribe({ x -> handleTickEvent() },
                { e -> e.printStackTrace() },
                { Log.d("RX", "complete") },
                { d -> Log.d("RX", "sub") })
    }


    fun handleTickEvent() {
        if(NetService.getTemp().isNotEmpty()){
            _sensorsList.value = NetService.getTemp()
            _progress.value = false
        }else{
            _progress.value = true

            _cnt.postValue(cnt.value?.plus(1))
            return
        }
        _cnt.postValue(0)
    }

    fun updateConfig(){
        sensorsConfig = Requests.getSensorList()
    }

}