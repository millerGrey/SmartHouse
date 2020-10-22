package grey.smarthouse.ui.mainScreen

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import grey.smarthouse.R
import grey.smarthouse.utils.ViewModelFactory
import grey.smarthouse.data.Repository
import grey.smarthouse.model.App
import grey.smarthouse.data.remote.Requests
import grey.smarthouse.services.NetService
import grey.smarthouse.ui.SingleFragmentActivity
import androidx.lifecycle.observe
import grey.smarthouse.ui.mainScreen.relays.RelaysVM
import grey.smarthouse.ui.relaySettingsScreen.RelaySettingsActivity

/**
 * Created by GREY on 26.05.2018.
 */

class MainActivity : SingleFragmentActivity() {

    val relayVM by lazy {ViewModelProviders.of(this, ViewModelFactory(App.app, Repository(App.app.database, Requests))).get(RelaysVM::class.java)}
    private var mViewPager: ViewPager? = null
    override fun createFragment(): Fragment? {
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mViewPager = findViewById(R.id.view_pager)
        val fm = supportFragmentManager
        mViewPager!!.adapter = ViewPagerAdapter(fm)

        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        tabLayout.setupWithViewPager(mViewPager)
        Requests.retrofitInit(App.app.mDeviceURL)
        val intent = Intent(applicationContext, NetService::class.java)
        intent.putExtra(NOTIFICATION_FLAG, App.app.mIsNotifOn)
        intent.putExtra(NOTIFICATION_TEMP, App.app.mNotifTemp)
        startService(intent)
        relayVM.openRelayEvent.observe(this){
            it?.let{
                openRelaySettings(it)
            }
        }

    }

    companion object {
        private val NOTIFICATION_FLAG = "nFlag"
        private val NOTIFICATION_TEMP = "nTemp"
    }

    private fun openRelaySettings(num: Int){
        val intent = RelaySettingsActivity.NewIntent(this, num)
        startActivity(intent)
    }
}
