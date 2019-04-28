package grey.smarthouse.model;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.SharedPreferences;

import grey.smarthouse.database.AppDatabase;



public class App extends Application {
    final static String SAVED_URL = "saved_url";
    private static App instance;
    private AppDatabase database;
    private static SharedPreferences sPreferences;
    public static String mDeviceURL;

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

    public static void savePref(SharedPreferences sPref){
        SharedPreferences.Editor ed = sPreferences.edit();
        ed.putString(SAVED_URL, mDeviceURL);
        ed.commit();

    }
    public static void loadPref() {
        mDeviceURL = sPreferences.getString(SAVED_URL, "");
    }
}
