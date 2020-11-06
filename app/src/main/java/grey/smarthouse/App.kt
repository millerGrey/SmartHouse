package grey.smarthouse

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

import androidx.room.Room

import grey.smarthouse.data.local.AppDatabase


class App : Application() {
    lateinit var database: AppDatabase
        private set
    private var sPreferences: SharedPreferences? = null
    var mDeviceURL: String = ""
    var mNotifTemp: Int = 0
    var mIsNotifOn: Boolean = false
    var mIsTestSet: Boolean = false

    override fun onCreate() {
        super.onCreate()
        app = this
        sPreferences = this.getSharedPreferences("Preferenses", Context.MODE_PRIVATE)
        loadPref()
        database = Room.databaseBuilder(this, AppDatabase::class.java, "database.db")
                .allowMainThreadQueries()
                .build()
    }


    fun savePref() {
        val ed = sPreferences!!.edit()
        ed.putString(SAVED_URL, mDeviceURL)
        ed.putInt(SAVED_NOTIF_TEMP, mNotifTemp)
        ed.putBoolean(SAVED_NOTIF_ON, mIsNotifOn)
        ed.putBoolean(SAVED_TEST_SET, mIsTestSet)
        ed.commit()

    }

    fun loadPref() {
        sPreferences!!.getString(SAVED_URL, "")?.let{
            mDeviceURL = it
        }
        mNotifTemp = sPreferences!!.getInt(SAVED_NOTIF_TEMP, 25)
        mIsNotifOn = sPreferences!!.getBoolean(SAVED_NOTIF_ON, false)
        mIsTestSet = sPreferences!!.getBoolean(SAVED_TEST_SET, false)
    }

    companion object {
        internal val SAVED_URL = "saved_url"
        internal val SAVED_NOTIF_ON = "saved_notif_on"
        internal val SAVED_NOTIF_TEMP = "saved_notif_temp"
        internal val SAVED_TEST_SET = "saved_test_set"
        lateinit var app: App
            private set
    }
}
