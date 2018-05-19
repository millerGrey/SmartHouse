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

        Relay relay = new Relay(UUID.fromString(uuidString));
        relay.setDescription(desc);
        return relay;
    }
}
