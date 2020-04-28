package grey.smarthouse.ui.relaySettingsScreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import java.util.UUID;

import grey.smarthouse.ui.SingleFragmentActivity;

public class RelaySettingsActivity extends SingleFragmentActivity {

    private static final String EXTRA_RELAY_ID = "grey.smarthouse.relay_id";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFragment();
    }

    @Override
    protected Fragment createFragment() {
        UUID relayId = (UUID) getIntent().getSerializableExtra(EXTRA_RELAY_ID);
        return RelaySettingsFragment.newInstance(relayId);
    }

    public static Intent NewIntent(Context packageContext, UUID relayId) {
        Intent intent = new Intent(packageContext, RelaySettingsActivity.class);
        intent.putExtra(EXTRA_RELAY_ID, relayId);
        return intent;
    }
}
