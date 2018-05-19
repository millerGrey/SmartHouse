package com.example.grey.smarthouse.Retrofit;

import retrofit2.Retrofit;


/**
 * Created by GREY on 06.05.2018.
 */

public class Requests  {

    private static final String TAG = "req";

    private static SmartHouseApi smartHouseApi;
    private static Retrofit retrofit;

    public static void Requests(){

    }

    public static void RetrofitInit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://vineryhome.ddns.net:8081")
                //.addConverterFactory(GsonConverterFactory.create())
                .build();
        smartHouseApi = retrofit.create(SmartHouseApi.class);
    }

    public static SmartHouseApi getApi() {
        return smartHouseApi;
    }

}
