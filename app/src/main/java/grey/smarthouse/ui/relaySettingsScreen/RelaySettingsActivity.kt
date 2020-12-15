package grey.smarthouse.ui.relaySettingsScreen

import android.content.Context
import android.content.Intent
import android.os.Bundle

import androidx.fragment.app.Fragment
import grey.smarthouse.R
import grey.smarthouse.ui.SingleFragmentActivity

class RelaySettingsActivity : SingleFragmentActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addFragment()
    }

    override fun createFragment(): Fragment? {
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
