package grey.smarthouse.model;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.SharedPreferences;

import grey.smarthouse.database.AppDatabase;



public class App extends Application {
    final static String SAVED_URL = "saved_url";
    final static String SAVED_NOTIF_ON = "saved_notif_on";
    final static String SAVED_NOTIF_TEMP = "saved_notif_temp";
    final static String SAVED_TEST_SET = "saved_test_set";
    private static App instance;
    private AppDatabase database;
    private static SharedPreferences sPreferences;
    public static String mDeviceURL;
    public static int mNotifTemp;
    public static Boolean mIsNotifOn;
    public static Boolean mIsTestSet;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        sPreferences = this.getSharedPreferences("Preferenses", MODE_PRIVATE);
        loadPref();
        database = Room.databaseBuilder(this, AppDatabase.class, "database")
            .allowMainThreadQueries()
            .build();
    }
    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public static void savePref(){
        SharedPreferences.Editor ed = sPreferences.edit();
        ed.putString(SAVED_URL, mDeviceURL);
        ed.putInt(SAVED_NOTIF_TEMP, mNotifTemp);
        ed.putBoolean(SAVED_NOTIF_ON, mIsNotifOn);
        ed.putBoolean(SAVED_TEST_SET, mIsTestSet);
        ed.commit();

    }
    public static void loadPref() {
        mDeviceURL = sPreferences.getString(SAVED_URL, "");
        mNotifTemp = sPreferences.getInt(SAVED_NOTIF_TEMP, 25);
        mIsNotifOn = sPreferences.getBoolean(SAVED_NOTIF_ON, false);
        mIsTestSet = sPreferences.getBoolean(SAVED_TEST_SET, false);
    }
}