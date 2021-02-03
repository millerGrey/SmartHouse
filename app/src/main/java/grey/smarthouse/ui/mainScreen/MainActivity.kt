package grey.smarthouse.ui.mainScreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import grey.smarthouse.App
import grey.smarthouse.R
import grey.smarthouse.data.Repository
import grey.smarthouse.data.remote.Requests
import grey.smarthouse.ui.mainScreen.locations.LocationDialog
import grey.smarthouse.ui.mainScreen.locations.LocationVM
import grey.smarthouse.ui.mainScreen.relays.RelaysVM
import grey.smarthouse.ui.mainScreen.sensors.SensorDialog
import grey.smarthouse.ui.mainScreen.sensors.SensorsVM
import grey.smarthouse.ui.relaySettingsScreen.RelaySettingsActivity
import grey.smarthouse.utils.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by GREY on 26.05.2018.
 */

class MainActivity : AppCompatActivity() {

    val relayVM by lazy { ViewModelProviders.of(this, ViewModelFactory(App.app, Repository(App.app.database, Requests))).get(RelaysVM::class.java) }
    val locationsVM by lazy { ViewModelProviders.of(this, ViewModelFactory(App.app, Repository(App.app.database, Requests))).get(LocationVM::class.java) }
    val sensorsVM by lazy { ViewModelProviders.of(this, ViewModelFactory(App.app, Repository(App.app.database, Requests))).get(SensorsVM::class.java) }
    private lateinit var mViewPager: ViewPager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mViewPager = view_pager
        val fm = supportFragmentManager
        mViewPager.adapter = ViewPagerAdapter(fm, 1)
        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        tabLayout.setupWithViewPager(mViewPager)
        Requests.retrofitInit(App.app.mDeviceURL)
//        val intent = Intent(applicationContext, NetService::class.java)
//        intent.putExtra(NOTIFICATION_FLAG, App.app.mIsNotifOn)
//        intent.putExtra(NOTIFICATION_TEMP, App.app.mNotifTemp)
//        startService(intent)
        relayVM.openRelayEvent.observe(this) {
            it?.let {
                openRelaySettings(it)
            }
        }

        locationsVM.editLocationEvent.observe(this) {
            it?.let {
                val args = Bundle()
                args.putInt("id", it)
                val locDialog = LocationDialog().apply {
                    arguments = args
                    show(supportFragmentManager, "locationDialog")
                }
            }
        }

        sensorsVM.editSensorEvent.observe(this) {
            val args = Bundle()
            args.putInt("num", it)
            val sensorDialog = SensorDialog().apply {
                arguments = args
                show(supportFragmentManager, "sensorDialog")
            }
        }
    }

    companion object {
        private val NOTIFICATION_FLAG = "nFlag"
        private val NOTIFICATION_TEMP = "nTemp"
    }

    private fun openRelaySettings(num: Int) {
        val intent = RelaySettingsActivity.NewIntent(this, num)
        startActivity(intent)
    }
}
