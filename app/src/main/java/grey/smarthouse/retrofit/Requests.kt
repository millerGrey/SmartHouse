package grey.smarthouse.retrofit

import android.util.Log
import grey.smarthouse.model.Relay
import grey.smarthouse.model.RelayList
import grey.smarthouse.model.SensorList
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import java.util.*


/**
 * Created by GREY on 06.05.2018.
 */

object Requests {

    private val TAG = "req"

    lateinit var api: SmartHouseApi
        private set
    private var retrofit: Retrofit? = null
    private var URL: String? = null
    private val mt: List<String>? = null
    private var mConfig: List<String>? = null

    fun Requests() {}

    //    public static void retrofitInit() {
    ////        retrofitInit("vineryhome.ddns.net:8081");
    //        retrofitInit("192.168.0.200");
    //    }

    fun retrofitInit(url: String) {
        var url = url
        if (url.isEmpty()) {
            url = "192.168.0.200"
        }
        URL = "http://$url"
        retrofit = Retrofit.Builder()
                .baseUrl(URL!!)
                //.addConverterFactory(GsonConverterFactory.create())
                .build()
        api = retrofit!!.create(SmartHouseApi::class.java)
        Log.d("SH", "retrofit init")
    }

    fun relayStateRequest() {
        val stateReq = api!!.relayStateList()
        Log.d("TCP", ">>> " + stateReq.request().toString())
        stateReq.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                var resp: String? = null
                if (response.message() == "OK") {
                    try {
                        resp = response.body()!!.string()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                    Log.d("TCP", "<<< " + response.message() + " " + resp)
                    RelayList.mRelayStates = Arrays.asList(*resp!!.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
                Log.d("TCP", call.toString())
            }
        })
    }


    fun relayConfigRequest(mRelay: Relay, mRL: RelayList) {
        val res = Requests.api!!.configList(mRelay.number)
        Log.d("TCP", ">>> " + res.request().toString())

        res.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                var resp: String? = null

                try {
                    resp = response.body()!!.string()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                Log.d("TCP", "<<< " + response.message() + " " + resp)
                mConfig = Arrays.asList(*resp!!.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
                mRelay.mode = Integer.parseInt(mConfig!![1])
                mRelay.topTemp = Integer.parseInt(mConfig!![2])
                mRelay.botTemp = Integer.parseInt(mConfig!![3])
                mRelay.periodTime = Integer.parseInt(mConfig!![4])
                mRelay.durationTime = Integer.parseInt(mConfig!![5])
                mRL.updateRelay(mRelay)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    fun ds18b20Request(temp: SensorList) {
        val tempReq = api!!.ds18b20tempList()
        Log.d("TCP", ">>> " + tempReq.request().toString())
        tempReq.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                var resp: String? = null
                if (response.message() == "OK") {

                    try {
                        resp = response.body()!!.string()
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Log.d("TCP", response.body()!!.toString())
                    }

                    temp.list = Arrays.asList(*resp!!.replace(',', '.').split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())


                } else {
                    //TODO 404 неправильный адрес
                }
                Log.d("TCP", "<<< " + response.message() + " " + resp)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
                Log.d("TCP", call.toString())
                //TODO проверить соединение или адрес или устройство не в сети
            }
        })
    }
}
