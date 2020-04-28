package grey.smarthouse.retrofit;

import android.util.Log;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import grey.smarthouse.model.Relay;
import grey.smarthouse.model.RelayList;
import grey.smarthouse.model.SensorList;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * Created by GREY on 06.05.2018.
 */

public class Requests  {

    private static final String TAG = "req";

    private static SmartHouseApi smartHouseApi;
    private static Retrofit retrofit;
    private static String URL;
    private static List<String> mt;
    private static List<String> mConfig;

    public static void Requests(){
    }

//    public static void RetrofitInit() {
////        RetrofitInit("vineryhome.ddns.net:8081");
//        RetrofitInit("192.168.0.200");
//    }

    public static void RetrofitInit(String url) {
        if (url.isEmpty()) {
            url = "192.168.0.200";
        }
        URL = "http://" + url;
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                //.addConverterFactory(GsonConverterFactory.create())
                .build();
        smartHouseApi = retrofit.create(SmartHouseApi.class);
        Log.d("SH","retrofit init");
    }

    public static SmartHouseApi getApi() {
        return smartHouseApi;
    }

    public static void relayStateRequest() {
        Call<ResponseBody> stateReq = smartHouseApi.relayStateList();
        Log.d("TCP", ">>> " + stateReq.request().toString());
        stateReq.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String resp = null;
                if (response.message().equals("OK")) {
                    try {
                        resp = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d("TCP", "<<< " + response.message() + " " + resp);
                    RelayList.mRelayStates = Arrays.asList(resp.split("/"));
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Log.d("TCP", call.toString());
            }
        });
    }


    public static void relayConfigRequest(Relay mRelay, RelayList mRL) {
        Call<ResponseBody> res = Requests.getApi().configList(mRelay.getNumber());
        Log.d("TCP", ">>> " + res.request().toString());

        res.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String resp = null;

                try {
                    resp = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("TCP", "<<< " + response.message() + " " + resp);
                mConfig = Arrays.asList(resp.split("/"));
                mRelay.setMode(Integer.parseInt(mConfig.get(1)));
                mRelay.setTopTemp(Integer.parseInt(mConfig.get(2)));
                mRelay.setBotTemp(Integer.parseInt(mConfig.get(3)));
                mRelay.setPeriodTime(Integer.parseInt(mConfig.get(4)));
                mRelay.setDurationTime(Integer.parseInt(mConfig.get(5)));
                mRL.updateRelay(mRelay);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }

        });
    }

    public static void ds18b20Request(SensorList temp) {
        Call<ResponseBody> tempReq = smartHouseApi.ds18b20tempList();
        Log.d("TCP", ">>> " + tempReq.request().toString());
        tempReq.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                String resp = null;
                if (response.message().equals("OK")) {

                    try {
                        resp = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("TCP", response.body().toString());
                    }
                    temp.setList(Arrays.asList(resp.replace(',', '.').split("/")));


                } else {
                    //TODO 404 неправильный адрес
                }
                Log.d("TCP", "<<< " + response.message() + " " + resp);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Log.d("TCP", call.toString());
                //TODO проверить соединение или адрес или устройство не в сети
            }
        });
    }
}
