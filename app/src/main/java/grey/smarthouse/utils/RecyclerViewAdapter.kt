package grey.smarthouse.utils

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class RecyclerViewAdapter(var items: List<Any>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {


    abstract class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        abstract fun bind(pos: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolderFactory.createHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return ViewHolderFactory.getItemViewType(items[position])
    }

    fun refresh(list: List<Any>) {
        items = list
        notifyDataSetChanged()
    }

    companion object {
        const val SENSOR_LIST_TYPE = 0
        const val RELAY_LIST_TYPE = 1
        const val LOCATION_LIST_TYPE = 2
        const val HEADER_LIST_TYPE = 3
    }
}