package com.example.grey.smarthouse;

import java.util.List;

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
}
