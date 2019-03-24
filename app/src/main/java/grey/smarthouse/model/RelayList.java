package grey.smarthouse.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import grey.smarthouse.database.RelayBaseHelper;
import grey.smarthouse.database.RelayCursorWrapper;
import grey.smarthouse.database.RelayDbSchema.RelayTable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GREY on 30.04.2018.
 */

public class RelayList {
    private static  RelayList sRelayList;
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

        mDatabase = new RelayBaseHelper(context).getWritableDatabase();
        mRelays = getRelays();
        if(mRelays.size()==0) {
            for (int i = 0; i < 4; i++) {
                Relay relay = new Relay();
                relay.setMode(2);
                relay.setNumber(i + 1);
                addRelay(relay);
            }
        }
    }


    private static ContentValues getContentValues(Relay relay) {
        ContentValues values = new ContentValues();
        values.put(RelayTable.Cols.UUID, relay.getId().toString());
        values.put(RelayTable.Cols.DESCRIPTION, relay.getDescription());
        values.put(RelayTable.Cols.NUMBER, relay.getNumber());
        values.put(RelayTable.Cols.MODE, relay.getMode());
        values.put(RelayTable.Cols.TOP_TEMP, relay.getTopTemp());
        values.put(RelayTable.Cols.BOT_TEMP, relay.getBotTemp());
        values.put(RelayTable.Cols.PERIOD_TIME, relay.getPeriodTime());
        values.put(RelayTable.Cols.DURATION_TIME, relay.getDurationTime());
        values.put(RelayTable.Cols.SENS_NUM, relay.getSensNum());
        return values;
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
        StackTraceElement[] a = new Throwable().getStackTrace();

            Log.d("tag",a[1].getClassName()+"# "+a[1].getMethodName());
            Log.d("tag",a[2].getClassName()+"# "+a[2].getMethodName());


        return new RelayCursorWrapper(cursor);
    }

    public List<Relay> getRelays()
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

//    public Relay getRelay(UUID id) {
////    Relay mRelay = new Relay();
//        RelayCursorWrapper cursor = queryRelays(
//                RelayTable.Cols.UUID + " = ?",
//                new String[] { id.toString() }
//        );
//        try {
//            if (cursor.getCount() == 0) {
//                return null;
//            }
//            cursor.moveToFirst();
////            mRelay = cursor.getRelay();
//            return cursor.getRelay();
//        } finally {
//            cursor.close();
//        }
//    }

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
