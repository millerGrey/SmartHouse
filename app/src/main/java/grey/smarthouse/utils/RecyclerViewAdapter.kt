package grey.smarthouse.utils

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import grey.smarthouse.R
import grey.smarthouse.databinding.ListItemLocationBinding
import grey.smarthouse.ui.mainScreen.relays.RelaysVM
import grey.smarthouse.ui.mainScreen.sensors.SensorsVM
import grey.smarthouse.databinding.ListItemRelayBinding
import grey.smarthouse.databinding.ListItemSensorBinding
import grey.smarthouse.ui.mainScreen.locations.LocationVM


class RecyclerViewAdapter(val viewModel: ViewModel, private val map: Map<Int, Int>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {


    abstract class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        abstract fun bind(pos: Int)
    }

    class SensorViewHolder(v: View, private val vm: ViewModel) : ViewHolder(v) {
        private val binding = ListItemSensorBinding.bind(v)
        override fun bind(pos: Int) {

            (vm as SensorsVM).sensorsList.value?.let { list ->
                if (list.isEmpty()) {
                    Log.d("RV", "bind sensor ${list.size}")
                }
                Log.d("RV", "bind sensor $pos ${list[pos]} ${list.size}")
                binding.sensor = list[pos]
            }
            binding.executePendingBindings()
        }
    }

    class RelayViewHolder(v: View, private val vm: ViewModel) : ViewHolder(v) {
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

    class LocationViewHolder(v: View, private val vm: ViewModel) : ViewHolder(v) {
        private val binding = ListItemLocationBinding.bind(v)
        override fun bind(pos: Int) {

            with(vm as LocationVM) {
                locationsList.value?.let { list ->
                    binding.location = list[pos]
//                    binding.itemClickListener = vm.editLocationEvent(list[pos].name)
                }
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        lateinit var view: View
        return when (viewType) {
            SENSOR_LIST_TYPE -> {
                view = layoutInflater.inflate(R.layout.list_item_sensor, parent, false)
                Log.d("RV", "CREATE sensor")
                SensorViewHolder(view, viewModel)
            }
            RELAY_LIST_TYPE -> {
                view = layoutInflater.inflate(R.layout.list_item_relay, parent, false)
                Log.d("RV", "CREATE relay")
                RelayViewHolder(view, viewModel)
            }
            LOCATION_LIST_TYPE -> {
                view = layoutInflater.inflate(R.layout.list_item_location, parent, false)
                Log.d("RV", "CREATE relay")
                LocationViewHolder(view, viewModel)
            }
            else -> SensorViewHolder(view, viewModel)
        }

//        binding = delegate.createBinding(viewModel, view)

//        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        delegate.bind(position)
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        try {
            return map.keys.size
        } catch (e: Exception) {
            e.printStackTrace()
            return 0
        }
    }

    override fun getItemViewType(position: Int): Int {
        return map[position] ?: 0
    }

    fun refresh() {
        notifyDataSetChanged()
    }

    //    fun setSensors(values: List<String>) {
////        mTemp = values
//        notifyDataSetChanged()
//    }
    companion object {
        val SENSOR_LIST_TYPE = 0
        val RELAY_LIST_TYPE = 1
        val LOCATION_LIST_TYPE = 2

    }
}