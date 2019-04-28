package grey.smarthouse.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import grey.smarthouse.database.AppDatabase;

/**
 * Created by GREY on 30.04.2018.
 */

public class RelayList {
    private static  RelayList sRelayList;
    private static AppDatabase sDb;
    List<Relay> mRelays;
    public static List<String>  mRelayStates;

    public static RelayList getInstance() {
        if (sRelayList == null)
        {
            sDb = App.getInstance().getDatabase();
            sRelayList = new RelayList();
        }
        return sRelayList;
    }

    private RelayList() {

        mRelays = getRelays();
        if(mRelays.size()==0) {
            for (int i = 0; i < 4; i++) {
                Relay relay = new Relay();
                relay.setMode(2);
                relay.setNumber(i + 1);
                addRelay(relay);
            }
        }
        mRelayStates = new ArrayList<String>();
        for(int i=0;i<5;i++){
            mRelayStates.add(i,"OFF");
        }
    }

    public Relay getRelay(UUID id) {
        Relay relay = sDb.mRelayDao().getById(id);
        return relay;
    }

    public List<Relay> getRelays()
    {
        return sDb.mRelayDao().getAll();
    }

    public void updateRelay(Relay relay) {
        sDb.mRelayDao().update(relay);
    }

    public void addRelay(Relay relay) {
        sDb.mRelayDao().insert(relay);
    }
}
