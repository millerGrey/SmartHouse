package grey.smarthouse.utils

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import grey.smarthouse.App
import grey.smarthouse.R
import grey.smarthouse.data.LocationWithLists
import grey.smarthouse.data.Relay
import grey.smarthouse.data.Sensor
import grey.smarthouse.databinding.ListItemLocationBinding
import grey.smarthouse.databinding.ListItemRelayBinding
import grey.smarthouse.databinding.ListItemSensorBinding
import grey.smarthouse.ui.mainScreen.locations.LocationVM
import grey.smarthouse.ui.mainScreen.relays.RelaysVM
import grey.smarthouse.ui.mainScreen.sensors.SensorsVM
import grey.smarthouse.utils.RecyclerViewAdapter.Companion.HEADER_LIST_TYPE
import grey.smarthouse.utils.RecyclerViewAdapter.Companion.LOCATION_LIST_TYPE
import grey.smarthouse.utils.RecyclerViewAdapter.Companion.RELAY_LIST_TYPE
import grey.smarthouse.utils.RecyclerViewAdapter.Companion.SENSOR_LIST_TYPE
import javax.inject.Inject

object ViewHolderFactory {

    class SensorViewHolder(v: View) : RecyclerViewAdapter.ViewHolder(v) {

        @Inject
        lateinit var sensorsVM: SensorsVM

        private val binding = ListItemSensorBinding.bind(v)

        override fun bind(pos: Int) {
            sensorsVM = (itemView.context.applicationContext as App).appComponent.getSensorsVM()
            sensorsVM.sensorsList.value?.let { list ->
                if (list.isEmpty()) {
                    Log.d("RV", "bind sensor ${list.size}")
                }
                Log.d("RV", "bind sensor $pos ${list[pos]} ${list.size}")
                val sensor = list[pos]
                binding.sensor = sensor
                binding.vm = sensorsVM
                binding.value = when (sensor.type) {
                    "temperature" -> String.format(
                        itemView.resources.getString(R.string.degree_with_digital_float),
                        sensor.value
                    )
                    "humidity" -> String.format(
                        itemView.resources.getString(R.string.percent_with_digital_float),
                        sensor.value
                    )
                    else -> "${sensor.value}"
                }
            }
            binding.executePendingBindings()
        }
    }

    class RelayViewHolder(v: View) : RecyclerViewAdapter.ViewHolder(v) {

        @Inject
        lateinit var relaysVM: RelaysVM

        private val binding = ListItemRelayBinding.bind(v)

        override fun bind(pos: Int) {
            relaysVM = (itemView.context.applicationContext as App).appComponent.getRelaysVM()
            relaysVM.relayList.value?.let { list ->
                binding.relay = list[pos]
                binding.buttonClickListener =
                    View.OnClickListener { relaysVM.relayToggle(list[pos]) }
                binding.itemClickListener =
                    View.OnClickListener { relaysVM.openRelay(list[pos].number) }
                binding.state = list[pos].state
            }
            binding.executePendingBindings()
        }

    }

    class LocationViewHolder(v: View) :
        RecyclerViewAdapter.ViewHolder(v) {

        @Inject
        lateinit var locationVM: LocationVM

        private val binding = ListItemLocationBinding.bind(v)

        override fun bind(pos: Int) {
            locationVM = (itemView.context.applicationContext as App).appComponent.getLocationVM()
            locationVM.locationsList.value?.let { list ->
                binding.locationWithLists = list[pos]
            }
            binding.vm = locationVM
            binding.executePendingBindings()
        }
    }

    class HeaderViewHolder(private val v: View) : RecyclerViewAdapter.ViewHolder(v) {
        override fun bind(pos: Int) {
            (v as TextView).text = "Оборудование"
        }
    }

    fun createHolder(
        parent: ViewGroup,
        itemViewType: Int
    ): RecyclerViewAdapter.ViewHolder {
        return when (itemViewType) {
            SENSOR_LIST_TYPE -> {
                val v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_sensor, parent, false)
                SensorViewHolder(v)
            }
            RELAY_LIST_TYPE -> {
                val v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_relay, parent, false)
                RelayViewHolder(v)
            }
            LOCATION_LIST_TYPE -> {
                val v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_location, parent, false)
                LocationViewHolder(v)
            }
            HEADER_LIST_TYPE -> {
                val v = LayoutInflater.from(parent.context)
                    .inflate(android.R.layout.simple_list_item_1, parent, false)
                HeaderViewHolder(v)
            }
            //TODO убрать else
            else -> SensorViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_sensor, parent, false)
            )
        }
    }

    fun getItemViewType(item: Any): Int {
        return when (item) {
            is Sensor -> SENSOR_LIST_TYPE
            is Relay -> RELAY_LIST_TYPE
            is LocationWithLists -> LOCATION_LIST_TYPE
            is String -> HEADER_LIST_TYPE
            else -> HEADER_LIST_TYPE
        }
    }
}