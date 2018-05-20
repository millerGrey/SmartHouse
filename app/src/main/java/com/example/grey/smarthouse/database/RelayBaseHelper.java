package com.example.grey.smarthouse.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.grey.smarthouse.database.RelayDbSchema.RelayTable;

/**
 * Created by GREY on 21.04.2018.
 */

public class RelayBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "relayBase.db";
    public RelayBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + RelayTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                RelayTable.Cols.UUID + ", " +
                RelayTable.Cols.DESCRIPTION + ", " +
                RelayTable.Cols.NUMBER + ", " +
                RelayTable.Cols.MODE + ", " +
                RelayTable.Cols.HOT + ", " +
                RelayTable.Cols.TOP_TEMP + ", " +
                RelayTable.Cols.BOT_TEMP + ", " +
                RelayTable.Cols.PERIOD_TIME + ", " +
                RelayTable.Cols.DURATION_TIME + ", " +
                RelayTable.Cols.SENS_NUM +
                ")"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
