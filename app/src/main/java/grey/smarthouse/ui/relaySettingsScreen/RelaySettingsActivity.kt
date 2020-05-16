package grey.smarthouse.ui.relaySettingsScreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import grey.smarthouse.ui.SingleFragmentActivity
import java.util.*

class RelaySettingsActivity : SingleFragmentActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addFragment()
    }

    override fun createFragment(): Fragment? {
        val relayId = intent.getSerializableExtra(EXTRA_RELAY_ID) as UUID
        return RelaySettingsFragment.newInstance(relayId)
    }

    companion object {

        private val EXTRA_RELAY_ID = "grey.smarthouse.relay_id"

        fun NewIntent(packageContext: Context, relayId: UUID): Intent {
            val intent = Intent(packageContext, RelaySettingsActivity::class.java)
            intent.putExtra(EXTRA_RELAY_ID, relayId)
            return intent
        }
    }
}
