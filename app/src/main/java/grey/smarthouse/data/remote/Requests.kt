package grey.smarthouse.data.remote

import android.util.Log
import grey.smarthouse.data.DataSource
import grey.smarthouse.data.Relay
import grey.smarthouse.data.SensorConfig
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

object Requests: DataSource {

    private val TAG = "req"

    lateinit var api: SmartHouseApi
        private set
    private var retrofit: Retrofit? = null
    private var URL: String? = null
    private val mt: List<String>? = null
    private var mConfig: List<String>? = null


    fun retrofitInit(url: String) {
        var url = url
        if (url.isEmpty()) {
            url = "192.168.0.201"
        }
        URL = "http://$url"
        retrofit = Retrofit.Builder()
                .baseUrl(URL!!)
                //.addConverterFactory(GsonConverterFactory.create())
                .build()
        api = retrofit!!.create(SmartHouseApi::class.java)
        Log.d("SH", "retrofit init")
    }

    fun relayStateRequest(): List<String> {
        var list: List<String> = emptyList()
        val stateReq = api.relayStateList()
        Log.d("TCP", ">>> " + stateReq.request().toString())
        try {
            val resp = stateReq.execute()
            val log = resp.body()!!.string()
            list = log.split("/".toRegex()).dropLastWhile { it.isEmpty() }.subList(1,9)
            Log.d("TCP", "<<< " + resp.message() + " | " + log)
        } catch (e: Exception) {
            e.printStackTrace()
        }

//        stateReq.enqueue(object : Callback<ResponseBody> {
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                var resp: String? = null
//                if (response.message() == "OK") {
//                    try {
//                        resp = response.body()!!.string()
//                    } catch (e: IOException) {
//                        e.printStackTrace()
//                    }
//
//                    Log.d("TCP", "<<< " + response.message() + " " + resp)
//                    list = resp!!.split("/".toRegex()).dropLastWhile { it.isEmpty() }
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                t.printStackTrace()
//                Log.d("TCP", call.toString())
//            }
//        })
        return list
    }


    fun ds18b20Request(temp: SensorList) {
        val tempReq = api.ds18b20tempList()
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

                    temp.list = resp!!.replace(',', '.').split("/".toRegex()).dropLastWhile { it.isEmpty() }


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


    override fun getSensorList(): List<SensorConfig>{
        var list = emptyList<SensorConfig>().toMutableList()
        val res =api.getSensors()
        res.enqueue(object : Callback<ResponseBody>{

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val config = response.body()?.string()
                for(line in config?.split("\r\n")!!){
                    if(line!="")
                        list.add(parceSensor(line))
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
            }
        })
        return list
    }
    fun parceSensor(str: String): SensorConfig{
        val ar = str.split(",")

        return SensorConfig(ar[0].toInt(), ar[1], ar[2])
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

  //  ----------------------------------------------------------------------------


//    override fun get(id: UUID): Relay {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }


    override fun get(num: Int): Relay {
        val relay = Relay()
        val res = api.configList(num)
        Log.d("TCP", ">>> " + res.request().toString())
        try{
            val resp = res.execute()
            val log = resp.body()!!.string()
            mConfig =log.split("/".toRegex()).dropLastWhile { it.isEmpty() }
            relay.number = Integer.parseInt(mConfig!![0])
            relay.mode = Integer.parseInt(mConfig!![1])
            relay.topTemp = Integer.parseInt(mConfig!![2])
            relay.botTemp = Integer.parseInt(mConfig!![3])
            relay.periodTime = Integer.parseInt(mConfig!![4])
            relay.durationTime = Integer.parseInt(mConfig!![5])
            Log.d("TCP", "<<< " + resp.message() + " | " + log)

        }catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

//        res.enqueue(object : Callback<ResponseBody> {
//
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                lateinit var resp: String
//
//                try {
//                    resp = response.body()!!.string()
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//
//                Log.d("TCP", "<<< " + response.message() + " " + resp)
//                mConfig = resp.split("/".toRegex()).dropLastWhile { it.isEmpty() }
//                relay.mode = Integer.parseInt(mConfig!![1])
//                relay.topTemp = Integer.parseInt(mConfig!![2])
//                relay.botTemp = Integer.parseInt(mConfig!![3])
//                relay.periodTime = Integer.parseInt(mConfig!![4])
//                relay.durationTime = Integer.parseInt(mConfig!![5])
//            }
//
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                t.printStackTrace()
//            }
//        })
        return relay
    }


    override fun getAll(): List<Relay> {

        val relayList = emptyList<Relay>().toMutableList()
        relayList.add(get(1))
        relayList.add(get(2))
        relayList.add(get(3))
        relayList.add(get(4))
        return relayList
    }

    override fun insert(relay: Relay) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(relay: Relay) {

        val mode = relay.mode
        var modeS: String = ""
        when (mode) {
            Relay.TEMP_MODE -> modeS = "temp"
            Relay.TIME_MODE -> modeS = "time"
            Relay.HAND_MODE -> modeS = "hand"
        }
        val tempReq = api.relaySetConfig(relay.number,
                modeS,
                relay.topTemp,
                relay.botTemp,
                relay.periodTime,
                relay.durationTime,
                relay.sensNum,
                relay.description)
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
//                    Toast.makeText(context!!.applicationContext, "Настройки сохранены", Toast.LENGTH_SHORT).show()
                } else {
//                    Toast.makeText(context!!.applicationContext, "Ошибка сохранения!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
//                Toast.makeText(context!!.applicationContext, "Ошибка сохранения!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun delete(relay: Relay) {
        return
    }




}

