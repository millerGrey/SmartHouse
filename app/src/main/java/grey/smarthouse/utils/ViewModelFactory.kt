package grey.smarthouse.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import grey.smarthouse.App
import grey.smarthouse.data.Repository
import grey.smarthouse.ui.mainScreen.locations.LocationVM
import grey.smarthouse.ui.mainScreen.relays.RelaysVM
import grey.smarthouse.ui.mainScreen.sensors.SensorsVM
import grey.smarthouse.ui.relaySettingsScreen.RelaySettingsVM

class ViewModelFactory(
        private val application: App,
        private val repository: Repository): ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        RelaySettingsVM::class.java -> RelaySettingsVM(
                repository
        ) as T

        SensorsVM::class.java -> SensorsVM(
                repository
        ) as T

        RelaysVM::class.java -> RelaysVM(
                repository
        ) as T

        LocationVM::class.java -> LocationVM(
                application, repository
        ) as T
        else -> throw RuntimeException("Cannot create an instance of $modelClass")
    }
}