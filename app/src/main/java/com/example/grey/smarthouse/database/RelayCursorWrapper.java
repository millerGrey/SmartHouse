package com.example.grey.smarthouse.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.grey.smarthouse.Model.Relay;
import com.example.grey.smarthouse.database.RelayDbSchema.RelayTable;


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
        boolean hot = (getInt(getColumnIndex(RelayTable.Cols.HOT)) & 0x1)!=0;
        int topTemp = getInt(getColumnIndex(RelayTable.Cols.TOP_TEMP));
        int botTemp  = getInt(getColumnIndex(RelayTable.Cols.BOT_TEMP));
        int periodTime = getInt(getColumnIndex(RelayTable.Cols.PERIOD_TIME));
        int durationTime = getInt(getColumnIndex(RelayTable.Cols.DURATION_TIME));
        int sensNum = getInt(getColumnIndex(RelayTable.Cols.SENS_NUM));

        Relay relay = new Relay(UUID.fromString(uuidString));
        relay.setDescription(desc);
        relay.setNumber(number);
        relay.setMode(mode);
        relay.setHot(hot);
        relay.setTopTemp(topTemp);
        relay.setBotTemp(botTemp);
        relay.setTopTemp(periodTime);
        relay.setTopTemp(durationTime);
        relay.setBotTemp(sensNum);
        return relay;
    }
}
