package grey.smarthouse.utils


import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

import grey.smarthouse.R
import grey.smarthouse.data.LocationWithLists
import grey.smarthouse.data.Relay
import grey.smarthouse.data.Sensor

//relayItem
object IconItemBindingAdapter {

    @BindingAdapter("app:icon")
    @JvmStatic fun setItems(view: View, relay: Relay) {
        when(relay.mode){
            0->{
                if(relay.topTemp > relay.botTemp)
                    view.setBackgroundResource(R.drawable.ic_sun)
                else
                    view.setBackgroundResource(R.drawable.ic_snow)
            }
            1->{
                view.setBackgroundResource(R.drawable.ic_time)
            }
            2->{
                view.setBackgroundResource(R.drawable.ic_hand)
            }
        }
    }
}

//relaySet
object IconTempAdapter {
    @BindingAdapter("app:icon")
    @JvmStatic
    fun setImage(view: View, isTempUp: Boolean) {
        when (isTempUp) {
            true -> view.setBackgroundResource(R.drawable.ic_sun)
            false -> view.setBackgroundResource(R.drawable.ic_snow)
        }

    }
}

object IconSensorBindingAdapter {

    @BindingAdapter("app:sensorIcon")
    @JvmStatic
    fun setItems(view: ImageView, sensor: Sensor) {
        when (sensor.type) {
            "temperature" -> {
                view.setImageResource(R.drawable.ic_termo)
            }
            "humidity" -> {
                view.setImageResource(R.drawable.ic_humy)
            }
            else -> {//TODO убрать
                view.setImageResource(R.drawable.ic_termo)
            }
        }
    }
}

//sensorList
object SensorListBindingAdapter {

    @BindingAdapter("app:sensorItems")
    @JvmStatic
    fun setItems(listView: RecyclerView, items: List<Sensor>) {
        with(listView.adapter as RecyclerViewAdapter) {
            refresh()
        }
    }
}
//relayList
object RelayListBindingAdapter {

    @BindingAdapter("app:relayItems")
    @JvmStatic
    fun setItems(listView: RecyclerView, value: List<Relay>) {
        with(listView.adapter as RecyclerViewAdapter) {
//            setRelays(items)
            refresh()
        }
    }
}

object LocationListBindingAdapter {

    @BindingAdapter("app:items")
    @JvmStatic fun setItems(listView: RecyclerView, value: List<LocationWithLists>) {
        with(listView.adapter as RecyclerViewAdapter) {
//            setRelays(items)
            refresh()
        }
    }
}