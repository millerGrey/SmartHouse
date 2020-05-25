package grey.smarthouse.utils


import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

import grey.smarthouse.R
import grey.smarthouse.data.Relay

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
object IconTempAdapter{
        @BindingAdapter("app:icon")
        @JvmStatic fun setImage(view: View, isTempUp: Boolean){
            when(isTempUp){
                true -> view.setBackgroundResource(R.drawable.ic_sun)
                false -> view.setBackgroundResource(R.drawable.ic_snow)
            }

        }
}
//sensorList
object SensorListBindingAdapter {

    @BindingAdapter("app:items")
    @JvmStatic fun setItems(listView: RecyclerView, items: List<String>) {
        with(listView.adapter as RecyclerViewAdapter) {
            refresh()
        }
    }
}
//relayList
object RelayListBindingAdapter {

    @BindingAdapter("app:items")
    @JvmStatic fun setItems(listView: RecyclerView, value: List<String>) {
        with(listView.adapter as RecyclerViewAdapter) {
//            setRelays(items)
            refresh()
        }
    }
}