package grey.smarthouse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import grey.smarthouse.model.Relay
import grey.smarthouse.model.RelayList
import grey.smarthouse.retrofit.Requests
import java.util.*


class RelaySettingsVM: ViewModel() {


    private val _mode = MutableLiveData<Int>(2)
    val mode: LiveData<Int>
        get() = _mode

    private val _isTempUp = MutableLiveData(true)
    val isTempUp: LiveData<Boolean>
        get() = _isTempUp


    var num = 0
    var description = ""
    var topTemp = ""
    var botTemp = ""
    var periodTime = ""
    var durationTime = ""
    var relay = Relay()

    fun start(id: UUID){
        relay  = RelayList.instance.getRelay(id)
        num = relay.number
        description = relay.description
        _mode.value = relay.mode
        topTemp = relay.topTemp.toString()
        botTemp = relay.botTemp.toString()
        _isTempUp.value = relay.topTemp > relay.botTemp
        periodTime = relay.periodTime.toString()
        durationTime = relay.durationTime.toString()
    }

    fun setSettingsToRelay() {
        _isTempUp.value = topTemp > botTemp
        relay.mode = mode.value!!
        relay.topTemp = Integer.parseInt(topTemp)
        relay.botTemp = Integer.parseInt(botTemp)
        relay.periodTime = Integer.parseInt(periodTime)
        relay.durationTime = Integer.parseInt(durationTime)
        relay.description = description

        RelayList.instance.updateRelay(relay)//TODO local repo
        Requests.updateRelaySet(relay)//TODO remote repo
    }

    fun updateCheckBoxes(mode: Int){ //TODO isChecked
        if(this.mode.value != mode){
            _mode.value = mode
        }
    }
}
