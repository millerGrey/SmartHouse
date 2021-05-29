package grey.smarthouse.ui.relaySettingsScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import grey.smarthouse.data.Relay
import grey.smarthouse.data.Repository
import kotlinx.coroutines.launch


class RelaySettingsVM(val repository: Repository) : ViewModel() {

    private var _relay = MutableLiveData<Relay>()
    val relay: LiveData<Relay>
        get() = _relay

    fun start(number: Int) {
        viewModelScope.launch {
            _relay.value = repository.getRelay(number)
        }
    }

    fun setSettingsToRelay() {
        relay.value?.let {
            viewModelScope.launch { repository.update(relay.value!!) }
        }
    }

    fun updateCheckBoxes(value: Int) { //TODO isChecked
        _relay.value = relay.value?.apply { mode = value }
    }
}
