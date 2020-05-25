package grey.smarthouse.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import grey.smarthouse.data.DataSource
import grey.smarthouse.ui.mainScreen.RelaysVM
import grey.smarthouse.ui.mainScreen.SensorsVM
import grey.smarthouse.ui.relaySettingsScreen.RelaySettingsVM

class ViewModelFactory(
        private val application: Application,
        private val repository: DataSource): ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        RelaySettingsVM::class.java -> RelaySettingsVM(
                repository
        ) as T

        SensorsVM::class.java -> SensorsVM(

        ) as T

        RelaysVM::class.java -> RelaysVM(
                repository
        ) as T

        else -> throw RuntimeException("Cannot create an instance of $modelClass")
    }
}