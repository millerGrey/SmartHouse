package grey.smarthouse.ui.mainScreen

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import grey.smarthouse.R
import grey.smarthouse.model.App
import grey.smarthouse.retrofit.Requests
import grey.smarthouse.services.NetService
import grey.smarthouse.ui.SingleFragmentActivity

/**
 * Created by GREY on 26.05.2018.
 */

class MainActivity : SingleFragmentActivity() {


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
    }

    companion object {
        private val NOTIFICATION_FLAG = "nFlag"
        private val NOTIFICATION_TEMP = "nTemp"
    }
}
