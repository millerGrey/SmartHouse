package grey.smarthouse.ui.mainScreen

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment

import grey.smarthouse.R
import grey.smarthouse.model.App
import grey.smarthouse.retrofit.Requests
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by GREY on 11.08.2018.
 */

class SettingsFragment : Fragment(), TextView.OnEditorActionListener {

    private var mEditURL: EditText? = null
    private var mEditIP: EditText? = null
    private var mEditNotifTemp: EditText? = null
    private var mReset: Button? = null
    private var mNotifOn: SwitchCompat? = null
    private var mTestSet: SwitchCompat? = null

    internal var lastVisibleState = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_settings, container, false)
        mEditURL = v.findViewById<View>(R.id.URL) as EditText
        mEditURL!!.setOnEditorActionListener(TextView.OnEditorActionListener { textView, i, keyEvent ->
            if (i == 6) {
                App.app.mDeviceURL = mEditURL!!.text.toString()
                Requests.retrofitInit(App.app.mDeviceURL)
                return@OnEditorActionListener false
            }
            false
        })
        mEditURL!!.setText(App.app.mDeviceURL)

        mNotifOn = v.findViewById<View>(R.id.switchNotif) as SwitchCompat
        mNotifOn!!.setOnCheckedChangeListener { compoundButton, b -> App.app.mIsNotifOn = b }
        mNotifOn!!.isChecked = App.app.mIsNotifOn

        mEditNotifTemp = v.findViewById<View>(R.id.tempNotif) as EditText
        mEditNotifTemp!!.setOnEditorActionListener(this)
        mEditNotifTemp!!.setText(Integer.toString(App.app.mNotifTemp))
        mTestSet = v.findViewById<View>(R.id.switchTestSet) as SwitchCompat
        mTestSet!!.isChecked = App.app.mIsTestSet
        mTestSet!!.setOnCheckedChangeListener { compoundButton, b ->
            App.app.mIsTestSet = b
            if (App.app.mIsTestSet) {
                mEditIP!!.visibility = View.VISIBLE
                mReset!!.visibility = View.VISIBLE
            } else {
                mEditIP!!.visibility = View.INVISIBLE
                mReset!!.visibility = View.INVISIBLE
            }
        }


        mEditIP = v.findViewById<View>(R.id.IP) as EditText

        mEditIP!!.setOnEditorActionListener(TextView.OnEditorActionListener { textView, i, keyEvent ->
            if (i == 6) {
                val tempReq = Requests.api?.setIP(mEditIP!!.text.toString())
                Log.d("TCP", ">>> " + tempReq?.request().toString())
                tempReq?.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.message() == "OK") {
                            Log.d("TCP", "<<< " + response.message())
                            Toast.makeText(context!!.applicationContext, "Настройки сохранены", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context!!.applicationContext, "Ошибка сохранения!", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(context!!.applicationContext, "Ошибка сохранения!", Toast.LENGTH_SHORT).show()
                        t.printStackTrace()
                    }
                })
                return@OnEditorActionListener false
            }
            false
        })
        mReset = v.findViewById<View>(R.id.reset) as Button
        if (App.app.mIsTestSet) {
            mEditIP!!.visibility = View.VISIBLE
            mReset!!.visibility = View.VISIBLE
        } else {
            mEditIP!!.visibility = View.INVISIBLE
            mReset!!.visibility = View.INVISIBLE
        }
        mReset!!.setOnClickListener {
            val tempReq = Requests.api?.reset()
            Log.d("TCP", ">>> " + tempReq?.request().toString())

            tempReq?.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    Log.d("TCP", "<<< " + response.message())
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }
        return v
    }

    override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent): Boolean {
        if (actionId == 6) {
            App.app.mNotifTemp = Integer.parseInt(mEditNotifTemp!!.text.toString())
            return false
        }
        return false
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {

        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lastVisibleState = true
        } else {
            if (lastVisibleState && !isVisibleToUser) {
                App.app.savePref()
            }
            lastVisibleState = false
        }

    }


}
