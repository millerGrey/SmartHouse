package com.example.grey.smarthouse;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Path;

public class RelaySettingsActivity extends Requests {
    Relay mRelay;
    private static final String EXTRA_RELAY_ID = "com.example.grey.smarthouse.relay_id";
    MyTask mTask;
    List<String> mCconfig;
    String mConfigStr;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        UUID relayId = (UUID)getIntent().getSerializableExtra(EXTRA_RELAY_ID);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRelay = RelayList.getInstance(this).getRelay(relayId);
//        mTask = new MyTask();

        mCconfig = new ArrayList<String>();

        Call<ResponseBody> res = Requests.getApi().configList(mRelay.getNumber());
        res.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    mConfigStr = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mCconfig = Arrays.asList(mConfigStr.split("/"));
                mRelay.setMode(Integer.parseInt(mCconfig.get(1)));
                mRelay.setTopTemp(Integer.parseInt(mCconfig.get(2)));
                mRelay.setBotTemp(Integer.parseInt(mCconfig.get(3)));
                mRelay.setPeriodTime(Integer.parseInt(mCconfig.get(4)));
                mRelay.setDurationTime(Integer.parseInt(mCconfig.get(5)));
                UUID relayId = (UUID)getIntent().getSerializableExtra(EXTRA_RELAY_ID);
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
                if (fragment == null) {
                    fragment = RelaySettingsFragment.newInstance(relayId);
                    fragmentManager.beginTransaction()
                            .add(R.id.fragment_container, fragment)
                            .commit();
                    //Log.d(TAG, "addFragment");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }

        });
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                mConfigStr = response.body();
//                mCconfig = Arrays.asList(mConfigStr.split("/"));
////                mRelay.setMode(Integer.parseInt(mCconfig.get(1)));
////                mRelay.setTopTemp(Integer.parseInt(mCconfig.get(2)));
////                mRelay.setBotTemp(Integer.parseInt(mCconfig.get(3)));
////                mRelay.setPeriodTime(Integer.parseInt(mCconfig.get(4)));
////                mRelay.setDurationTime(Integer.parseInt(mCconfig.get(5)));
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Toast.makeText(RelaySettingsActivity.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
//            }
//        });


//        FragmentManager fragmentManager = getSupportFragmentManager();
//        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
//        if (fragment == null) {
//            fragment = RelaySettingsFragment.newInstance(relayId);
//            fragmentManager.beginTransaction()
//                    .add(R.id.fragment_container, fragment)
//                    .commit();
//            //Log.d(TAG, "addFragment");
//        }
    }

    public static Intent NewIntent(Context packageContext, UUID relayId)
    {
        Intent intent = new Intent(packageContext, RelaySettingsActivity.class);
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
                fragment = RelaySettingsFragment.newInstance(relayId);
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .commit();
                //Log.d(TAG, "addFragment");
            }
        }
    }
}
