package com.example.grey.smarthouse;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;
import java.util.UUID;

public class RelaySetActivity extends AppCompatActivity {
    Relay mRelay;
    private static final String EXTRA_RELAY_ID = "com.example.grey.smarthouse.relay_id";
    MyTask mTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UUID relayId = (UUID)getIntent().getSerializableExtra(EXTRA_RELAY_ID);
        super.onCreate(savedInstanceState);
        mRelay = RelayList.getInstance(this).getRelay(relayId);
        mTask = new MyTask();
        mTask.execute();
        setContentView(R.layout.activity_main);
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
//        if (fragment == null) {
//            fragment = RelaySetFragment.newInstance(relayId);
//            fragmentManager.beginTransaction()
//                    .add(R.id.fragment_container, fragment)
//                    .commit();
//            //Log.d(TAG, "addFragment");
//        }
    }

    public static Intent NewIntent(Context packageContext, UUID relayId)
    {
        Intent intent = new Intent(packageContext, RelaySetActivity.class);
        intent.putExtra(EXTRA_RELAY_ID, relayId);
        return intent;
    }

    private  class MyTask extends AsyncTask<Void,List<String>,Void> {

        List<String> val;
        @Override
        protected Void doInBackground(Void... params) {

            val = new Requests().getConfig(mRelay.getNumber());
            mRelay.setMode(Integer.parseInt(val.get(1)));
            mRelay.setTopTemp(Integer.parseInt(val.get(2)));
            mRelay.setBotTemp(Integer.parseInt(val.get(3)));
            mRelay.setPeriodTime(Integer.parseInt(val.get(4)));
            mRelay.setDurationTime(Integer.parseInt(val.get(5)));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            UUID relayId = (UUID)getIntent().getSerializableExtra(EXTRA_RELAY_ID);
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
    }
}
