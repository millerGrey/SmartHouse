package grey.smarthouse.utils

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView


class RecyclerViewAdapter(val viewModel: ViewModel, private val map: Map<Int, Int>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {


    abstract class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        abstract fun bind(pos: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
         return ViewHolderFactory.createHolder(parent, viewModel, viewType)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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

    companion object {
        val SENSOR_LIST_TYPE = 0
        val RELAY_LIST_TYPE = 1
        val LOCATION_LIST_TYPE = 2
        val HEADER_LIST_TYPE = 3

    }
}