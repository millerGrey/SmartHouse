package grey.smarthouse.utils

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModel
import grey.smarthouse.R
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

object ViewHolderFactory {

    class SensorViewHolder(v: View, private val vm: ViewModel) : RecyclerViewAdapter.ViewHolder(v) {
        private val binding = ListItemSensorBinding.bind(v)
        override fun bind(pos: Int) {

            (vm as SensorsVM).sensorsList.value?.let { list ->
                if (list.isEmpty()) {
                    Log.d("RV", "bind sensor ${list.size}")
                }
                Log.d("RV", "bind sensor $pos ${list[pos]} ${list.size}")
                binding.sensor = list[pos]
                binding.vm = vm
            }
            binding.executePendingBindings()
        }
    }

    class RelayViewHolder(v: View, private val vm: ViewModel) : RecyclerViewAdapter.ViewHolder(v) {
        private val binding = ListItemRelayBinding.bind(v)
        override fun bind(pos: Int) {

            with(vm as RelaysVM) {
                relayList.value?.let { list ->
                    binding.relay = list[pos]
                    binding.buttonClickListener = View.OnClickListener { vm.relayToggle(list[pos]) }
                    binding.itemClickListener = View.OnClickListener { vm.openRelay(list[pos].number) }
                    binding.state = list[pos].state

                }
            }
            binding.executePendingBindings()
        }
    }

    class LocationViewHolder(v: View, private val vm: ViewModel) : RecyclerViewAdapter.ViewHolder(v) {
        private val binding = ListItemLocationBinding.bind(v)
        override fun bind(pos: Int) {

            with(vm as LocationVM) {
                locationsList.value?.let { list ->
                    binding.locationWithLists = list[pos]
                }
                binding.vm = vm
            }
            binding.executePendingBindings()
        }
    }

    class HeaderViewHolder(private val v: View) : RecyclerViewAdapter.ViewHolder(v) {
        override fun bind(pos: Int) {
            (v as TextView).text = "Оборудование"
        }
    }


    fun createHolder(parent: ViewGroup, vm: ViewModel, itemViewType: Int): RecyclerViewAdapter.ViewHolder {
        return when (itemViewType) {
            SENSOR_LIST_TYPE -> {
                val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_sensor, parent, false)
                SensorViewHolder(v, vm)
            }
            RELAY_LIST_TYPE -> {
                val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_relay, parent, false)
                RelayViewHolder(v, vm)
            }
            LOCATION_LIST_TYPE -> {
                val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_location, parent, false)
                LocationViewHolder(v, vm)
            }
            HEADER_LIST_TYPE -> {
                val v = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
                HeaderViewHolder(v)
            }
            //TODO убрать else
            else -> SensorViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_sensor, parent, false), vm);
        }
    }

}