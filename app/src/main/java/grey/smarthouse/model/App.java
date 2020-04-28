package grey.smarthouse.model;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.room.Room;

import grey.smarthouse.database.AppDatabase;



public class App extends Application {
    final static String SAVED_URL = "saved_url";
    final static String SAVED_NOTIF_ON = "saved_notif_on";
    final static String SAVED_NOTIF_TEMP = "saved_notif_temp";
    final static String SAVED_TEST_SET = "saved_test_set";
    private static App sApp;
    private AppDatabase database;
    private SharedPreferences sPreferences;
    public String mDeviceURL;
    public int mNotifTemp;
    public Boolean mIsNotifOn;
    public Boolean mIsTestSet;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        sPreferences = this.getSharedPreferences("Preferenses", MODE_PRIVATE);
        loadPref();
        database = Room.databaseBuilder(this, AppDatabase.class, "database")
            .allowMainThreadQueries()
            .build();
        RelayList.setRepo(getDatabase());
    }
    public static App getApp() {
        return sApp;
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public void savePref(){
        SharedPreferences.Editor ed = sPreferences.edit();
        ed.putString(SAVED_URL, mDeviceURL);
        ed.putInt(SAVED_NOTIF_TEMP, mNotifTemp);
        ed.putBoolean(SAVED_NOTIF_ON, mIsNotifOn);
        ed.putBoolean(SAVED_TEST_SET, mIsTestSet);
        ed.commit();

    }
    public  void loadPref() {
        mDeviceURL = sPreferences.getString(SAVED_URL, "");
        mNotifTemp = sPreferences.getInt(SAVED_NOTIF_TEMP, 25);
        mIsNotifOn = sPreferences.getBoolean(SAVED_NOTIF_ON, false);
        mIsTestSet = sPreferences.getBoolean(SAVED_TEST_SET, false);
    }
}
