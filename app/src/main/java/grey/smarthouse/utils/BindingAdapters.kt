package grey.smarthouse.utils


import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

import grey.smarthouse.R
import grey.smarthouse.data.Sensor
import grey.smarthouse.ui.mainScreen.SensorListFragment

//object IconItemBindingAdapter {
//
//    @BindingAdapter("app:itemIcon")
//    @JvmStatic fun setItems(view: View, mode: Int, isTempUp: Boolean) {
//        when(mode){
//            0->{
//                if(isTempUp)
//                    view.setBackgroundResource(R.drawable.ic_sun)
//                else
//                    view.setBackgroundResource(R.drawable.ic_snow)
//            }
//            1->{
//                view.setBackgroundResource(R.drawable.ic_time)
//            }
//            2->{
//                view.setBackgroundResource(R.drawable.ic_hand)
//            }
//        }
//    }
//}

object IconTempAdapter{
        @BindingAdapter("app:icon")
        @JvmStatic fun setImage(view: View, isTempUp: Boolean){
            when(isTempUp){
                true -> view.setBackgroundResource(R.drawable.ic_sun)
                false -> view.setBackgroundResource(R.drawable.ic_snow)
            }

        }
}

object ListBindingAdapter {

    @BindingAdapter("app:items")
    @JvmStatic fun setItems(listView: RecyclerView, items: List<String>) {
        with(listView.adapter as RecyclerViewAdapter) {
            android.util.Log.d("RV","Refresh")
            setSensors(items)
        }
    }
}