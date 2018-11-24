package grey.smarthouse.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import grey.smarthouse.model.Model;
import grey.smarthouse.model.Relay;
import grey.smarthouse.ui.fragments.RelaySettingsFragment;
import grey.smarthouse.retrofit.Requests;

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
    private static final String EXTRA_RELAY_ID = "grey.smarthouse.relay_id";
    List<String> mConfig;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID relayId = (UUID) getIntent().getSerializableExtra(EXTRA_RELAY_ID);
//        mRelay = RelayList.getInstance(this).getRelay(relayId);
        mRelay = Model.getRelay(relayId);

        mConfig = new ArrayList<String>();
//        Requests.RetrofitInit();
        Call<ResponseBody> res = Requests.getApi().configList(mRelay.getNumber());
        res.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    mConfig = Arrays.asList(response.body().string().split("/"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mRelay.setMode(Integer.parseInt(mConfig.get(1)));
                mRelay.setTopTemp(Integer.parseInt(mConfig.get(2)));
                mRelay.setBotTemp(Integer.parseInt(mConfig.get(3)));
                mRelay.setPeriodTime(Integer.parseInt(mConfig.get(4)));
                mRelay.setDurationTime(Integer.parseInt(mConfig.get(5)));
//                RelayList.getInstance(RelaySettingsActivity.this).updateRelay(mRelay);
                mModel.updateRelay(RelaySettingsActivity.this,mRelay);
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
