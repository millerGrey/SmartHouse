package com.example.grey.smarthouse;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by GREY on 30.04.2018.
 */

public class RelayList {
    private static  RelayList sRelayList;
    private Context mContext;
    List<Relay> mRelays;

    public static RelayList getInstance(Context context) {
        if (sRelayList == null)
        {
            sRelayList = new RelayList(context);
        }
        return sRelayList;
    }

    private RelayList(Context context) {
        mRelays = new ArrayList<>();
        mContext = context.getApplicationContext();
        for (int i = 0; i < 4; i++) {
            Relay relay = new Relay();
            relay.setDescription("Реле " + i);
            relay.setMode(1); // Для каждого второго объекта
            mRelays.add(relay);
        }
    }

    public List<Relay> getRelays()
    {
        return mRelays;
    }

    public Relay getRelay(UUID id)
    {
        for(Relay relay  : mRelays)
        {
            if(relay.getId().equals(id))
            {
                return relay;
            }
        }
        return null;
    }

}
