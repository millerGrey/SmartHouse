package com.example.grey.smarthouse.Retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by GREY on 15.05.2018.
 */

public interface SmartHouseApi {

    @GET("/cgi-bin/relay{relayNumber}/config/")
    Call<ResponseBody> configList(@Path("relayNumber") int relayNum);
    //Call<List<PostModel>> getData(@Query("name") String resourceName, @Query("num") int count);

    @GET("/cgi-bin/ds1820/value/")
    Call<ResponseBody> ds18b20tempList();
    //Call<List<PostModel>> getData(@Query("name") String resourceName, @Query("num") int count);
}
