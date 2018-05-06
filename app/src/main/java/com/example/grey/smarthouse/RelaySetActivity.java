package com.example.grey.smarthouse;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.UUID;

public class RelaySetActivity extends AppCompatActivity {

    private static final String EXTRA_RELAY_ID = "com.example.grey.smarthouse.relay_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UUID relayId = (UUID)getIntent().getSerializableExtra(EXTRA_RELAY_ID);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = RelaySetFragment.newInstance(relayId);
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
            //Log.d(TAG, "addFragment");
        }
    }

    public static Intent NewIntent(Context packageContext, UUID relayId)
    {
        Intent intent = new Intent(packageContext, RelaySetActivity.class);
        intent.putExtra(EXTRA_RELAY_ID, relayId);
        return intent;
    }
}
