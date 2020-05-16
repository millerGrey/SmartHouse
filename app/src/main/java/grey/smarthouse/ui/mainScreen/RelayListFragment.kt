package grey.smarthouse.ui.mainScreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import grey.smarthouse.R
import grey.smarthouse.model.Relay
import grey.smarthouse.model.RelayList
import grey.smarthouse.retrofit.Requests
import grey.smarthouse.ui.RefreshFragment
import grey.smarthouse.ui.relaySettingsScreen.RelaySettingsActivity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by GREY on 30.04.2018.
 */

class RelayListFragment : RefreshFragment() {

    private var mRelayRecyclerView: RecyclerView? = null
    private var mAdapter: RelayAdapter? = null
    private lateinit var mRelayList: RelayList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("tag", "listFragmentCreate")
        mRelayList = RelayList.instance

    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_relay_list, container, false)
        mRelayRecyclerView = v.findViewById<View>(R.id.relay_recycler_view) as RecyclerView
        mRelayRecyclerView!!.layoutManager = LinearLayoutManager(activity)
        updateUI()
        return v
    }


    private fun updateUI() {
        val relays = mRelayList!!.relays
        if (mAdapter == null) {
            mAdapter = RelayAdapter(relays)
            mRelayRecyclerView!!.adapter = mAdapter
        } else {
            mAdapter!!.notifyDataSetChanged()
        }
        mAdapter!!.setRelays(relays)
    }

    inner class RelayHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_relay, parent, false)), View.OnClickListener {
        private lateinit var mRelay: Relay
        private val mName: TextView
        private val mMode: ImageView
        private val mFirstParam: TextView
        private val mSecondParam: TextView
        private val mDescription: TextView
        private val mButtonState: ToggleButton

        init {
            mName = itemView.findViewById(R.id.item_name)
            mDescription = itemView.findViewById(R.id.textDescription)
            mMode = itemView.findViewById(R.id.item_image_mode)
            mFirstParam = itemView.findViewById(R.id.item_first_param)
            mSecondParam = itemView.findViewById(R.id.item_second_param)
            mButtonState = itemView.findViewById(R.id.buttonState)
            itemView.setOnClickListener(this)
        }

        fun bind(relay: Relay) {
            mRelay = relay
            val mode = mRelay.mode
            mName.text = "Реле " + mRelay.number
            mDescription.text = mRelay.description
            if (mode == Relay.TEMP_MODE) {
                if (mRelay.botTemp > mRelay!!.topTemp) {
                    mMode.setImageResource(R.drawable.ic_snow)
                } else {
                    mMode.setImageResource(R.drawable.ic_sun)
                }
                mFirstParam.text = Integer.toString(mRelay.topTemp) + resources.getString(R.string.degree)
                mSecondParam.text = Integer.toString(mRelay.botTemp) + resources.getString(R.string.degree)
            } else if (mode == Relay.TIME_MODE) {
                mMode.setImageResource(R.drawable.ic_time)
                mFirstParam.text = Integer.toString(mRelay.periodTime) + " " + resources.getString(R.string.minutes)
                mSecondParam.text = Integer.toString(mRelay.durationTime) + " " + resources.getString(R.string.minutes)
            } else {
                mMode.setImageResource(R.drawable.ic_hand)
                mFirstParam.text = ""
                mSecondParam.text = ""
            }

            if (RelayList.mRelayStates.get(mRelay!!.number) == "1") {
                mButtonState.isChecked = true
            } else {
                mButtonState.isChecked = false
            }
            mButtonState.setOnClickListener {
                mMode.setImageResource(R.drawable.ic_hand)
                mRelay.mode = Relay.HAND_MODE
                mRelayList.updateRelay(mRelay)
                mFirstParam.text = ""
                mSecondParam.text = ""

                if (mButtonState.isChecked) {
                    RelayList.mRelayStates.set(mRelay!!.number, "1")
                    relayOnRequest(mRelay!!.number)
                } else {
                    RelayList.mRelayStates.set(mRelay!!.number, "0")
                    relayOffRequest(mRelay!!.number)
                }
            }
        }

        override fun onClick(v: View) {
            val intent = RelaySettingsActivity.NewIntent(requireActivity(), mRelay!!.id)
            startActivity(intent)
        }

    }


    private inner class RelayAdapter(private var mRelays: List<Relay>?) : RecyclerView.Adapter<RelayHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelayHolder {
            val layoutInflater = LayoutInflater.from(activity)
            return RelayHolder(layoutInflater, parent)
        }

        override fun onBindViewHolder(holder: RelayHolder, position: Int) {
            val relay = mRelays!![position]
            holder.bind(relay)
        }

        override fun getItemCount(): Int {
            return mRelays!!.size
        }

        fun setRelays(relays: List<Relay>) {
            mRelays = relays
        }
    }

    override fun handleTickEvent() {
        updateUI()
    }

    fun relayOnRequest(num: Int) {
        val relayOnReq = Requests.api.relayOn(num)
        Log.d("TCP", ">>> " + relayOnReq.request().toString())
        relayOnReq.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d("TCP", "<<< " + response.message())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun relayOffRequest(num: Int) {
        val relayOffReq = Requests.api.relayOff(num)
        Log.d("TCP", ">>> " + relayOffReq.request().toString())
        relayOffReq.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d("TCP", "<<< " + response.message())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            val relays = mRelayList!!.relays
            for (r in relays) {
                Requests.relayConfigRequest(r, mRelayList)
            }
        }
    }
}
