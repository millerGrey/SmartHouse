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

    public static void Requests(){
    }

    public static void RetrofitInit() {
        RetrofitInit("vineryhome.ddns.net:8081");
    }

    public static void RetrofitInit(String url) {
        if(url.isEmpty())
        {
            url = "192.168.0.200";
        }
        URL = "http://" + url;
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                //.addConverterFactory(GsonConverterFactory.create())
                .build();
        smartHouseApi = retrofit.create(SmartHouseApi.class);
    }

    public static SmartHouseApi getApi() {
        return smartHouseApi;
    }

}
