package grey.smarthouse.model;

import android.content.Context;

import java.util.List;
import java.util.UUID;

public class Model {


    public static List<Relay> mRelayConfigs;
//    public static ArrayList<String> mRelayStates;
    public static String mDeviceURL;


    private static  Model sModel;

    public static Model getInstance(Context context) {

        if (sModel == null)
        {
            sModel = new Model(context);
        }
        return sModel;
    }

    private Model(Context context) {
        mRelayConfigs = RelayList.getInstance(context).getRelays();
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


}
