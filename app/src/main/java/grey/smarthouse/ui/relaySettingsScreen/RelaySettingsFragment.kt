package grey.smarthouse.ui.relaySettingsScreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import grey.smarthouse.R
import grey.smarthouse.model.Relay
import grey.smarthouse.model.RelayList
import grey.smarthouse.retrofit.Requests
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*

/**
 * Created by GREY on 29.04.2018.
 */

class RelaySettingsFragment : Fragment() {


    internal lateinit var mRelay: Relay
    private var mNumber: TextView? = null
    private var mDescriptionField: EditText? = null
    private var mTopTemp: EditText? = null
    private var mBotTemp: EditText? = null
    private var mPeriodTime: EditText? = null
    private var mDurationTime: EditText? = null
    private var mHandModeCheckBox: CheckBox? = null
    private var mTempModeCheckBox: CheckBox? = null
    private var mTimeModeCheckBox: CheckBox? = null
    private var mTempIcon: ImageView? = null
    private var mSaveButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val relayId = arguments!!.getSerializable(ARG_RELAY_ID) as UUID?
        //        mRelay = RelayList.getApp(getActivity()).getRelay(relayId);
        mRelay = RelayList.instance.getRelay(relayId!!)
    }

    override fun onResume() {
        super.onResume()
        updateUI(mRelay.mode)
        mTopTemp!!.setText(mRelay.topTemp.toString())
        mBotTemp!!.setText(mRelay.botTemp.toString())
        mPeriodTime!!.setText(mRelay.periodTime.toString())
        mDurationTime!!.setText(mRelay.durationTime.toString())
        mDescriptionField!!.setText(mRelay.description)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_relay_set, container, false)

        mNumber = v.findViewById<View>(R.id.relayNumber) as TextView
        mNumber!!.text = String.format(resources.getString(R.string.relay_n), mRelay.number)
        mDescriptionField = v.findViewById<View>(R.id.relayDescription) as EditText
        mDescriptionField!!.setText(mRelay.description)
        mTopTemp = v.findViewById<View>(R.id.topTemp) as EditText
        mBotTemp = v.findViewById<View>(R.id.botTemp) as EditText
        mPeriodTime = v.findViewById<View>(R.id.periodTime) as EditText
        mDurationTime = v.findViewById<View>(R.id.durationTime) as EditText
        mSaveButton = v.findViewById<View>(R.id.saveButton) as Button
        mTempIcon = v.findViewById<View>(R.id.ic_temp) as ImageView

        mHandModeCheckBox = v.findViewById<View>(R.id.handModeCheckbox) as CheckBox
        mHandModeCheckBox!!.setOnClickListener {
            mRelay.mode = Relay.HAND_MODE
            updateUI(mRelay.mode)
        }
        mTempModeCheckBox = v.findViewById<View>(R.id.tempModeCheckbox) as CheckBox
        mTempModeCheckBox!!.setOnClickListener {
            mRelay.mode = Relay.TEMP_MODE
            updateUI(mRelay.mode)
        }
        mTimeModeCheckBox = v.findViewById<View>(R.id.timeModeCheckbox) as CheckBox
        mTimeModeCheckBox!!.setOnClickListener {
            mRelay.mode = Relay.TIME_MODE
            updateUI(mRelay.mode)
        }
        mSaveButton = v.findViewById<View>(R.id.saveButton) as Button
        mSaveButton!!.setOnClickListener {
            setSettingsToRelay()
            updateUI(mRelay.mode)
        }

        updateUI(mRelay.mode)

        return v
    }

    private fun updateUI(mode: Int) {
        val mTemp = mode == 0
        val mTime = mode == 0x01
        val mHand = mode == 0x02

        mHandModeCheckBox!!.isChecked = mHand
        mTempModeCheckBox!!.isChecked = mTemp
        mTimeModeCheckBox!!.isChecked = mTime
        mTopTemp!!.isEnabled = mTemp
        mBotTemp!!.isEnabled = mTemp
        mPeriodTime!!.isEnabled = mTime
        mDurationTime!!.isEnabled = mTime
        if (mRelay.topTemp > mRelay.botTemp) {
            mTempIcon!!.setImageResource(R.drawable.ic_sun)
        } else {
            mTempIcon!!.setImageResource(R.drawable.ic_snow)
        }
    }

    private fun setSettingsToRelay() {
        mRelay.mode = if (mHandModeCheckBox!!.isChecked) 2 else if (mTempModeCheckBox!!.isChecked) 0 else 1
        mRelay.topTemp = Integer.parseInt(mTopTemp!!.text.toString())
        mRelay.botTemp = Integer.parseInt(mBotTemp!!.text.toString())
        mRelay.periodTime = Integer.parseInt(mPeriodTime!!.text.toString())
        mRelay.durationTime = Integer.parseInt(mDurationTime!!.text.toString())
        mRelay.description = mDescriptionField!!.text.toString()
        RelayList.instance.updateRelay(mRelay)
        val mode = mRelay.mode
        var modeS: String = ""
        when (mode) {
            Relay.TEMP_MODE -> modeS = "temp"
            Relay.TIME_MODE -> modeS = "time"
            Relay.HAND_MODE -> modeS = "hand"
        }
        val tempReq = Requests.api?.relaySetConfig(mRelay.number,
                modeS,
                mRelay.topTemp,
                mRelay.botTemp,
                mRelay.periodTime,
                mRelay.durationTime,
                mRelay.sensNum,
                mRelay.description)
        Log.d("TCP", ">>> " + tempReq.request().toString())
        tempReq.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.message() == "OK") {
                    var resp: String? = null
                    try {
                        resp = response.body()!!.string()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                    Log.d("TCP", "<<< " + response.message() + " " + resp)
                    Toast.makeText(context!!.applicationContext, "Настройки сохранены", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context!!.applicationContext, "Ошибка сохранения!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(context!!.applicationContext, "Ошибка сохранения!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {

        private val ARG_RELAY_ID = "relay_id"


        fun newInstance(rId: UUID): RelaySettingsFragment {
            val args = Bundle()
            args.putSerializable(ARG_RELAY_ID, rId)
            val fragment = RelaySettingsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
