package grey.smarthouse.ui.mainScreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import grey.smarthouse.R
import grey.smarthouse.services.NetService
import grey.smarthouse.ui.RefreshFragment
import grey.smarthouse.ui.SensorDialog
import java.util.*


/**
 * Created by GREY on 26.05.2018.
 */

class SensorListFragment : RefreshFragment() {

    private var mSensorsRecyclerView: RecyclerView? = null
    private var mSensorAdapter: SensorAdapter? = null
    internal lateinit var mProgress: LinearLayout
    internal lateinit var mProgressText: TextView
    internal var mTemp: List<String> = ArrayList()
    private var mPage: Int = 0
    private var cnt = 0
    internal lateinit var dialog: DialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mPage = arguments!!.getInt(ARG_PAGE)
        }
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sensor_list, container, false)
        mProgress = view.findViewById(R.id.progressLayout)
        mProgressText = view.findViewById(R.id.textProgressMessage)
        mSensorsRecyclerView = view.findViewById(R.id.sensor_recycler_view)
        mSensorsRecyclerView!!.layoutManager = LinearLayoutManager(activity)
        dialog = SensorDialog()
        mSensorAdapter = SensorAdapter(mTemp)
        mSensorsRecyclerView!!.adapter = mSensorAdapter
        updateUI()
        return view
    }


    override fun handleTickEvent() {
        Log.d("RX", "handletick")
        updateUI()
    }

    fun updateUI() {

        if(NetService.getTemp().isNotEmpty()){
            mTemp = NetService.getTemp()
            mProgress.visibility = View.INVISIBLE
        }else{
            mProgress.visibility = View.VISIBLE
            if (cnt > 3) {
                mProgressText.setText(R.string.checkAdress)
            } else {
                mProgressText.setText(R.string.connection)
            }
            cnt++
            return
        }
        cnt = 0
        mSensorAdapter!!.setSensors(mTemp)
        mSensorAdapter!!.notifyDataSetChanged()
    }


    inner class SensorHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_sensor, parent, false)), View.OnClickListener {
        private val mValue: TextView

        init {
            mValue = itemView.findViewById(R.id.text_sensorValue)
            itemView.setOnClickListener(this)
        }

        fun bind(value: String) {
            mValue.text = "$value °С"
        }


        override fun onClick(v: View) {

        }
    }

    private inner class SensorAdapter(values: List<String>) : RecyclerView.Adapter<SensorHolder>() {
        private var mTemp: List<String>? = null

        init {
            setSensors(values)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorHolder {
            val layoutInflater = LayoutInflater.from(activity)
            return SensorHolder(layoutInflater, parent)
        }

        override fun onBindViewHolder(holder: SensorHolder, position: Int) {
            val value = mTemp!![position]
            holder.bind(value)
        }

        override fun getItemCount(): Int {
            try {
                return mTemp!!.size
            } catch (e: Exception) {
                e.printStackTrace()
                return 0
            }

        }

        fun setSensors(values: List<String>) {
            mTemp = values
        }
    }

    companion object {
        val ARG_PAGE = "ARG PAGE"

        fun newInstance(page: Int): SensorListFragment {
            val args = Bundle()
            args.putInt(ARG_PAGE, page)
            val fragment = SensorListFragment()
            fragment.arguments = args
            return fragment
        }
    }

}
