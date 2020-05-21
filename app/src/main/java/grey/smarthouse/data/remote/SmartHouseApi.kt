package grey.smarthouse.data.remote

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by GREY on 15.05.2018.
 */

interface SmartHouseApi {


    @get:GET("/data/ds18b20/dsList.csv")
    val sensorsList: Call<ResponseBody>

    @GET("/cgi-bin/reset")
    fun reset(): Call<ResponseBody>

    @GET("/cgi-bin/ip/set/{ipAddr}")
    fun setIP(@Path("ipAddr") ipAddr: String): Call<ResponseBody>

    @GET("/cgi-bin/ds1820/value/")
    fun ds18b20tempList(): Call<ResponseBody>

    @GET("/cgi-bin/state")
    fun relayStateList(): Call<ResponseBody>

    @GET("/cgi-bin/relay{relayNumber}/on/")
    fun relayOn(@Path("relayNumber") relayNum: Int): Call<ResponseBody>

    @GET("/cgi-bin/relay{relayNumber}/off/")
    fun relayOff(@Path("relayNumber") relayNum: Int): Call<ResponseBody>

    @GET("/cgi-bin/relay{relayNumber}/config/")
    fun configList(@Path("relayNumber") relayNum: Int): Call<ResponseBody>
    //Call<List<PostModel>> getData(@Query("name") String resourceName, @Query("num") int count);

    @GET("/cgi-bin/relay{relayNumber}/{mode}/temp_h/{tempHigh}/temp_l/{tempLow}/period/{periodTime}/action/{actionTime}/sensor_num/{sensNumber}/desc/{desc}")
    fun relaySetConfig(@Path("relayNumber") relayNum: Int,
                       @Path("mode") mode: String,
                       @Path("tempHigh") tempHigh: Int,
                       @Path("tempLow") tempLow: Int,
                       @Path("periodTime") periodTime: Int,
                       @Path("actionTime") actionTimer: Int,
                       @Path("sensNumber") sensNumber: Int,
                       @Path("desc") desc: String): Call<ResponseBody>


    @GET("/data/dsList.csv")
    fun getSensors(): Call<ResponseBody>
}
