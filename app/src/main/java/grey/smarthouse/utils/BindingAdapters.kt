package grey.smarthouse.utils


import android.view.View
import androidx.databinding.BindingAdapter

import grey.smarthouse.R

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