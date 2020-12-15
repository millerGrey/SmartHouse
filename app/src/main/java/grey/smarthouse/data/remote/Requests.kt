package grey.smarthouse.data.remote

import android.util.Log
import grey.smarthouse.data.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


/**
 * Created by GREY on 06.05.2018.
 */

object Requests : DataSource {

    private val TAG = "TCP"

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


    private fun parceSensor(str: String): Sensor {
        val array = str.split(",")
        return Sensor(number = array[0].toInt(),
                id = array[1],
                location = array[2],
                description = array[3],
                model = array[4],
                type = array[5])
    }

    private fun parceRelay(str: String): Relay {
        val array = str.split("/").dropLastWhile { it.isEmpty() }
        return Relay(array[0].toInt(),
                "",
                array[1].toInt(),
                array[2].toInt(),
                array[3].toInt(),
                array[4].toInt(),
                array[5].toInt(),
                1)
    }


    //  ----------------------------------------------------------------------------


    override suspend fun getRelay(num: Int): Relay {
        var relay = Relay()
        val resp = api.configList(num)
        val log = resp.string()
        relay = parceRelay(log)
        Log.d(TAG, "<<< get relay $num | $log")
        return relay
    }


    override suspend fun getAllRelays(list: List<Relay>?): List<Relay>? {

        Log.d(TAG, "list is empty")
        val relayList = emptyList<Relay>().toMutableList()
        try {
            relayList.add(getRelay(1))
            relayList.add(getRelay(2))
            relayList.add(getRelay(3))
            relayList.add(getRelay(4))
            val states = getRelayStates()
            relayList.forEachIndexed { index, relay ->
                relay.state = when (states[index]) {
                    "0" -> false
                    "1" -> true
                    else -> false
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return relayList
    }

    override suspend fun update(relay: Relay) {
        Log.d(TAG, ">>> update relay")
        val mode = relay.mode
        var modeStr: String = ""
        setRelayState(relay.number, relay.state)
        when (mode) {
            Relay.TEMP_MODE -> modeStr = "temp"
            Relay.TIME_MODE -> modeStr = "time"
            Relay.HAND_MODE -> modeStr = "hand"
        }
        try {
            val response = api.relaySetConfig(relay.number,
                    modeStr,
                    relay.topTemp,
                    relay.botTemp,
                    relay.periodTime,
                    relay.durationTime,
                    relay.sensNum,
                    relay.description)
            Log.d(TAG, "<<< " + response.string())
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun getRelayStates(): List<String> {
        Log.d(TAG, "start method")
        var list: List<String> = emptyList()
        val stateReq = api.relayStateList()
        val log = stateReq.string()
//        Log.d(TAG, ">>> request")
        list = log.split("/".toRegex()).dropLastWhile { it.isEmpty() }.subList(1, 9)
        Log.d(TAG, "<<< response" + log)
        Log.d(TAG, "end method")
        return list
    }

    private fun setRelayState(num: Int, enable: Boolean) {
        lateinit var relaySetState: Call<ResponseBody>
        relaySetState = if (enable) {
            api.relayOn(num)
        } else {
            api.relayOff(num)
        }
        Log.d(TAG, ">>> " + relaySetState.request().toString())
        relaySetState.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d(TAG, "<<< " + response.message())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    override suspend fun getSensor(num: Int): Sensor {
        TODO("Not yet implemented")
    }


    override suspend fun getAllSensors(): List<Sensor>? {
        lateinit var log: String
        lateinit var response: ResponseBody

        var sensorList: MutableList<Sensor>? = null
        try {
            response = api.getSensors()
            log = response.string()
            response = api.getSensorValues()
            if (log.isNotEmpty()) {
                sensorList = emptyList<Sensor>().toMutableList()
                for (line in log.split("\r\n")) {
                    if (line != "")
                        sensorList.add(parceSensor(line))
                }
            }
            Log.d(TAG, "<<< | $log")
            log = response.string()
            val values = log.replace(',', '.').split("/".toRegex()).dropLastWhile { it.isEmpty() }
            sensorList?.forEachIndexed { index, sensor ->
                sensor.value = values[index].toFloat()
            }
            Log.d(TAG, "<<< | $log")

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return sensorList
    }

    override fun update(sensor: Sensor) {
        TODO("Not yet implemented")
    }

    override fun insert(sensor: Sensor) {
        super.insert(sensor)
    }

    override fun delete(sensor: Sensor) {
        super.delete(sensor)
    }

    override suspend fun getLocation(name: String): Location {
        TODO("Not yet implemented")
    }

    override suspend fun getAllLocations(): List<LocationWithLists> {
        TODO("Not yet implemented")
    }

    override fun update(location: Location) {
        TODO("Not yet implemented")
    }

    override fun insert(location: Location) {
        super.insert(location)
    }

    override fun delete(location: Location) {
        super.delete(location)
    }
}

