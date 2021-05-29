package grey.smarthouse.utils


import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
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
                null
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
            refresh(items)
        }
    }
}
//relayList
object RelayListBindingAdapter {

    @BindingAdapter("app:relayItems")
    @JvmStatic
    fun setItems(listView: RecyclerView, items: List<Relay>) {
        with(listView.adapter as RecyclerViewAdapter) {
//            setRelays(items)
            refresh(items)
        }
    }
}

object LocationListBindingAdapter {

    @BindingAdapter("app:items")
    @JvmStatic
    fun setItems(listView: RecyclerView, items: List<LocationWithLists>) {
        (listView.adapter as RecyclerViewAdapter).refresh(items)
    }
}

object SensorDialogBindingAdapter {
    @JvmStatic
    @BindingAdapter("app:value")
    fun setValue(view: Spinner, position: Int) {
        view.setSelection(position)
    }

    @InverseBindingAdapter(attribute = "app:value", event = "valueAttrChanged")
    @JvmStatic
    fun getValue(view: Spinner): Int {

        return view.selectedItemPosition
    }


    @BindingAdapter("valueAttrChanged")
    @JvmStatic
    fun setInverseBindingListener(view: Spinner, valueAttrChanged: InverseBindingListener) {
        val listener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                valueAttrChanged.onChange()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                valueAttrChanged.onChange()
            }
        }
        view.onItemSelectedListener = listener
    }

}


