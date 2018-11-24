package grey.smarthouse.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.content.Context.MODE_PRIVATE;

public class Model {

    final static String SAVED_URL = "saved_url";

    public static List<Relay> mRelayConfigs;
    public static List<String>  mRelayStates;
    public static String mDeviceURL;


    private static  Model sModel;
    private static SharedPreferences sPreferences;


    public static Model getInstance(Context context) {

        if (sModel == null)
        {
            sModel = new Model(context);
        }
        return sModel;
    }

    private Model(Context context) {
        sPreferences = context.getSharedPreferences("Preferenses", MODE_PRIVATE);
        loadPref();
        mRelayConfigs = RelayList.getInstance(context).getRelays();
        mRelayStates = new ArrayList<String>();
        for(int i=0;i<5;i++){
            mRelayStates.add(i,"OFF");
        }
    }

    public static Relay getRelay(UUID uuid){
        for(Relay relay : mRelayConfigs){
            if(relay.getId().equals(uuid)){
                return relay;
            }
        }
        return null;
    }
    public static void updateRelay(Context context, Relay relay){
       RelayList.getInstance(context).updateRelay(relay);
    }
    public static void savePref(SharedPreferences sPref){
        SharedPreferences.Editor ed = sPreferences.edit();
        ed.putString(SAVED_URL, mDeviceURL);
        ed.commit();

    }
    public static void loadPref() {
        mDeviceURL = sPreferences.getString(SAVED_URL, "");
    }

}
