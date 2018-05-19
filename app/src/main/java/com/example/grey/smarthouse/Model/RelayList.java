package com.example.grey.smarthouse.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.grey.smarthouse.database.RelayBaseHelper;
import com.example.grey.smarthouse.database.RelayCursorWrapper;
import com.example.grey.smarthouse.database.RelayDbSchema.RelayTable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by GREY on 30.04.2018.
 */

public class RelayList {
    private static  RelayList sRelayList;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    List<Relay> mRelays;

    public static RelayList getInstance(Context context) {
        if (sRelayList == null)
        {
            sRelayList = new RelayList(context);
        }
        return sRelayList;
    }

    private RelayList(Context context) {

        mContext = context.getApplicationContext();
        mDatabase = new RelayBaseHelper(mContext).getWritableDatabase();
        mRelays = getRelaysfromDB();
        if(mRelays.size()==0) {
            for (int i = 0; i < 4; i++) {
                Relay relay = new Relay();
                mRelays.add(relay);
                ContentValues values = getContentValues(relay);
                mDatabase.insert(RelayTable.NAME, null, values);
            }
        }
        for(Relay relay: mRelays) {
            relay.setMode(1);
            relay.setNumber(mRelays.indexOf(relay) + 1);
        }
    }


    private RelayCursorWrapper queryRelays(String whereClause, String[] whereArgs) {

        Cursor cursor = mDatabase.query(
                RelayTable.NAME,
                null, // columns - с null выбираются все столбцы
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new RelayCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Relay relay) {
        ContentValues values = new ContentValues();
        values.put(RelayTable.Cols.UUID, relay.getId().toString());
        values.put(RelayTable.Cols.DESCRIPTION, relay.getDescription());
        return values;
    }

    private List<Relay> getRelaysfromDB()
    {
        List<Relay> relays = new ArrayList<>();
        RelayCursorWrapper cursor = queryRelays(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                relays.add(cursor.getRelay());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return relays;
    }
    public List<Relay> getRelays(){
        return mRelays;
    }

    public Relay getRelay(UUID id)
    {
        RelayCursorWrapper cursor = queryRelays(
                RelayTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getRelay();
        } finally {
            cursor.close();
        }
//        for(Relay relay  : mRelays)
//        {
//            if(relay.getId().equals(id))
//            {
//                return relay;
//            }
//        }
//        return null;
    }
    public void updateRelay(Relay relay) {
        String uuidString = relay.getId().toString();
        ContentValues values = getContentValues(relay);
        mDatabase.update(RelayTable.NAME, values,
                RelayTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    public void addRelay(Relay r) {
        ContentValues values = getContentValues(r);
        mDatabase.insert(RelayTable.NAME, null, values);
    }
}
