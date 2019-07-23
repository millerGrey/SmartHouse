package grey.smarthouse.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by GREY on 15.05.2018.
 */

public interface SmartHouseApi {

    @GET("/cgi-bin/reset")
    Call<ResponseBody> reset();

    @GET("/cgi-bin/ip/set/{ipAddr}")
    Call<ResponseBody> setIP(@Path("ipAddr") String ipAddr);

    @GET("/cgi-bin/ds1820/value/")
    Call<ResponseBody> ds18b20tempList();

    @GET("/cgi-bin/state/")
    Call<ResponseBody> relayStateList();

    @GET("/cgi-bin/relay{relayNumber}/on/")
    Call<ResponseBody> relayOn(@Path("relayNumber") int relayNum);

    @GET("/cgi-bin/relay{relayNumber}/off/")
    Call<ResponseBody> relayOff(@Path("relayNumber") int relayNum);

    @GET("/cgi-bin/relay{relayNumber}/config/")
    Call<ResponseBody> configList(@Path("relayNumber") int relayNum);
    //Call<List<PostModel>> getData(@Query("name") String resourceName, @Query("num") int count);

    @GET("/cgi-bin/relay{relayNumber}/{mode}/temp_h/{tempHigh}/temp_l/{tempLow}/period/{periodTime}/action/{actionTime}/sensor_num/{sensNumber}/desc/{desc}")
    Call<ResponseBody> relaySetConfig(@Path("relayNumber") int relayNum,
                                     @Path("mode") String mode,
                                     @Path("tempHigh") int tempHigh,
                                     @Path("tempLow") int tempLow,
                                     @Path("periodTime") int periodTime,
                                     @Path("actionTime") int actionTimer,
                                     @Path("sensNumber") int sensNumber,
                                     @Path("desc") String desc);


}
///{mode}/temp_h/{tempHigh}/temp_l/{tempLow}/period/{periodTime}/action/{actionTime}/sensor_num/{sensNumber}/{desc}/")