package grey.smarthouse.di

import dagger.BindsInstance
import dagger.Component
import grey.smarthouse.App
import grey.smarthouse.ui.mainScreen.locations.LocationVM
import grey.smarthouse.ui.mainScreen.relays.RelaysVM
import grey.smarthouse.ui.mainScreen.sensors.SensorsVM
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: App): AppComponent
    }

    fun getLocationVM(): LocationVM
    fun getSensorsVM(): SensorsVM
    fun getRelaysVM(): RelaysVM
}