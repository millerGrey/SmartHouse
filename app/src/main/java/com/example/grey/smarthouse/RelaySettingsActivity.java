package com.example.grey.smarthouse;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.example.grey.smarthouse.Model.Relay;
import com.example.grey.smarthouse.Model.RelayList;
import com.example.grey.smarthouse.Retrofit.Requests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RelaySettingsActivity extends SingleFragmentActivity {
    Relay mRelay;
    private static final String EXTRA_RELAY_ID = "com.example.grey.smarthouse.relay_id";
    List<String> mCconfig;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID relayId = (UUID) getIntent().getSerializableExtra(EXTRA_RELAY_ID);
        mRelay = RelayList.getInstance(this).getRelay(relayId);
        mCconfig = new ArrayList<String>();
//        Requests.RetrofitInit();
        Call<ResponseBody> res = Requests.getApi().configList(mRelay.getNumber());
        res.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    mCconfig = Arrays.asList(response.body().string().split("/"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mRelay.setMode(Integer.parseInt(mCconfig.get(1)));
                mRelay.setTopTemp(Integer.parseInt(mCconfig.get(2)));
                mRelay.setBotTemp(Integer.parseInt(mCconfig.get(3)));
                mRelay.setPeriodTime(Integer.parseInt(mCconfig.get(4)));
                mRelay.setDurationTime(Integer.parseInt(mCconfig.get(5)));
                RelayList.getInstance(RelaySettingsActivity.this).updateRelay(mRelay);
                addFragment();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }

        });
    }

    @Override
    protected Fragment createFragment() {
        UUID relayId = (UUID) getIntent().getSerializableExtra(EXTRA_RELAY_ID);
        return RelaySettingsFragment.newInstance(relayId);
    }

    public static Intent NewIntent(Context packageContext, UUID relayId)
    {
        Intent intent = new Intent(packageContext, RelaySettingsActivity.class);
        intent.putExtra(EXTRA_RELAY_ID, relayId);
        return intent;
    }
}
