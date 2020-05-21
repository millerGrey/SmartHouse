package grey.smarthouse.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import grey.smarthouse.BR
import grey.smarthouse.R


class RecyclerViewAdapter(list: List<String>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private var mTemp: List<String>
    private lateinit var binding: ViewDataBinding

    init{
        mTemp = list
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_sensor, parent, false)
        binding = DataBindingUtil.bind(view)!!
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mTemp[position])
    }
    override fun getItemCount(): Int {
        try {
            return mTemp.size
        } catch (e: Exception) {
            e.printStackTrace()
            return 0
        }

    }

    fun setSensors(values: List<String>) {
        mTemp = values
        notifyDataSetChanged()

    }

    class ViewHolder(val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(value: String) {
            binding.setVariable(BR.value,value)
            binding.executePendingBindings()
        }

    }

}