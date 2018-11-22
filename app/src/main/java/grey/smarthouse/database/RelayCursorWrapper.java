package grey.smarthouse.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

import grey.smarthouse.model.Relay;
import grey.smarthouse.database.RelayDbSchema.RelayTable;


import java.util.UUID;

/**
 * Created by GREY on 22.04.2018.
 */

public class RelayCursorWrapper extends CursorWrapper{
    public RelayCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Relay getRelay(){
        String uuidString = getString(getColumnIndex(RelayTable.Cols.UUID));
        String desc = getString(getColumnIndex(RelayTable.Cols.DESCRIPTION));
        int number = getInt(getColumnIndex(RelayTable.Cols.NUMBER));
        int mode = getInt(getColumnIndex(RelayTable.Cols.MODE));
        int hot = getInt(getColumnIndex(RelayTable.Cols.HOT));
        int topTemp = getInt(getColumnIndex(RelayTable.Cols.TOP_TEMP));
        int botTemp  = getInt(getColumnIndex(RelayTable.Cols.BOT_TEMP));
        int periodTime = getInt(getColumnIndex(RelayTable.Cols.PERIOD_TIME));
        int durationTime = getInt(getColumnIndex(RelayTable.Cols.DURATION_TIME));
        int sensNum = getInt(getColumnIndex(RelayTable.Cols.SENS_NUM));

        Relay relay = new Relay(UUID.fromString(uuidString));
        relay.setDescription(desc);
        relay.setNumber(number);
        relay.setMode(mode);
        relay.setHot(hot!=0);
        relay.setTopTemp(topTemp);
        relay.setBotTemp(botTemp);
        relay.setPeriodTime(periodTime);
        relay.setDurationTime(durationTime);
        relay.setSensNum(sensNum);
        Log.d("tag","getFromDB: " + relay.getId().toString());
        return relay;
    }
}
