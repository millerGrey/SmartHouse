package grey.smarthouse.model;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.SharedPreferences;

import grey.smarthouse.database.AppDatabase;



public class App extends Application {
    final static String SAVED_URL = "saved_url";
    private static App sApp;
    private static AppDatabase sDatabase;
    private static SharedPreferences sPreferences;
    public static String mDeviceURL;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        sPreferences = this.getSharedPreferences("Preferenses", MODE_PRIVATE);
        loadPref();
        sDatabase = Room.databaseBuilder(this, AppDatabase.class, "sDatabase")
            .allowMainThreadQueries()
            .build();
        RelayList.setRepo(getDatabase());
    }
    public static App getApp() {
        return sApp;
    }

    public AppDatabase getDatabase() {
        return sDatabase;
    }

    public static void savePref(SharedPreferences sPref){
        SharedPreferences.Editor ed = sPreferences.edit();
        ed.putString(SAVED_URL, mDeviceURL);
        ed.commit();

    }
    public static void loadPref() {
        mDeviceURL = sPreferences.getString(SAVED_URL, "");
    }
}
