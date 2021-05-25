package grey.smarthouse.ui.relaySettingsScreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment
import grey.smarthouse.R

class RelaySettingsActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default)
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
    }

    fun createFragment(): Fragment? {
        val relayNum = intent.getSerializableExtra(R.string.EXTRA_RELAY_ID.toString()) as Int
        return RelaySettingsFragment.newInstance(relayNum)
    }

    companion object {
        fun NewIntent(packageContext: Context, num: Int): Intent {
            val intent = Intent(packageContext, RelaySettingsActivity::class.java)
            intent.putExtra(R.string.EXTRA_RELAY_ID.toString(), num)
            return intent
        }
    }
}
