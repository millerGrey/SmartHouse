package grey.smarthouse.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import grey.smarthouse.R

/**
 * Created by GREY on 17.05.2018.
 */

abstract class SingleFragmentActivity : AppCompatActivity() {


    internal lateinit var PBar: ProgressBar

    protected abstract fun createFragment(): Fragment?
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default)
        PBar = findViewById<View>(R.id.progressRelaySettings) as ProgressBar
    }

    protected fun addFragment() {
        val fm = supportFragmentManager
        var fragment = fm.findFragmentById(R.id.fragment_container)
        if (fragment == null) {
            fragment = createFragment()
            fragment?.let {
                fm.beginTransaction()
                        .add(R.id.fragment_container, it)
                        .commit()
            }
        }
        PBar.visibility = View.INVISIBLE
    }

}
