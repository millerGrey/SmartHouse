package grey.smarthouse.retrofit;

import retrofit2.Retrofit;


/**
 * Created by GREY on 06.05.2018.
 */

public class Requests  {

    private static final String TAG = "req";

    private static SmartHouseApi smartHouseApi;
    private static Retrofit retrofit;
    private static String URL;
    private static String IP;

    public static void Requests(){
    }

    public static void RetrofitInit() {
        RetrofitInit("vineryhome.ddns.net:8081");
    }

    public static void RetrofitInit(String url) {
        URL = "http://"+url;
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                //.addConverterFactory(GsonConverterFactory.create())
                .build();
        smartHouseApi = retrofit.create(SmartHouseApi.class);
    }

    public static SmartHouseApi getApi() {
        return smartHouseApi;
    }


    public static void SetIP(String ip)
    {
        IP = ip;
    }

}
