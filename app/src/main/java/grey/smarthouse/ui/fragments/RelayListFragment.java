package grey.smarthouse.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import grey.smarthouse.model.Model;
import grey.smarthouse.model.Relay;
import grey.smarthouse.R;
import grey.smarthouse.ui.activities.RelaySettingsActivity;
import grey.smarthouse.retrofit.Requests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by GREY on 30.04.2018.
 */

public class RelayListFragment extends refreshFragment {

    private RecyclerView mRelayRecyclerView;
    private RelayAdapter mAdapter;
    private List<String> mState;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("tag","listFragmentCreate");
    }

    @Override
    public void onResume() {
        super.onResume();
        startProcess();
        updateUI();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_relay_list, container, false);
        mRelayRecyclerView = (RecyclerView)v.findViewById(R.id.relay_recycler_view);
        mRelayRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mState = new ArrayList<String>();
        for(int i=0;i<5;i++){
            mState.add(i,"OFF");
        }
        updateUI();
        return v;
    }


    private void updateUI(){
        List<Relay> relays = Model.mRelayConfigs;
        if(mAdapter == null){
            mAdapter = new RelayAdapter(relays);
            mRelayRecyclerView.setAdapter(mAdapter);
        }
        else
        {
            mAdapter.notifyDataSetChanged();
        }
        mAdapter.setRelays(relays);

    }

    public class RelayHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Relay mRelay;
        private TextView mName;
        private ImageView mMode;
        private TextView mFirstParam;
        private TextView mSecondParam;
        private TextView mDescription;
        private ToggleButton mButtonState;

        public RelayHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_relay,parent, false));
            mName = itemView.findViewById(R.id.item_name);
            mDescription = itemView.findViewById(R.id.textDescription);
            mMode = itemView.findViewById(R.id.item_image_mode);
            mFirstParam = itemView.findViewById(R.id.item_first_param);
            mSecondParam = itemView.findViewById(R.id.item_second_param);
            mButtonState = itemView.findViewById(R.id.buttonState);
            itemView.setOnClickListener(this);
        }

        public void bind(Relay relay){
            mRelay = relay;
            int mode = mRelay.getMode();
            mName.setText("Реле " + mRelay.getNumber());
            mDescription.setText(mRelay.getDescription());
            if(mode == Relay.TEMP_MODE){
                mMode.setImageResource(R.drawable.ic_sun);
                mFirstParam.setText(Integer.toString(mRelay.getTopTemp()) + getResources().getString(R.string.degree));
                mSecondParam.setText(Integer.toString(mRelay.getBotTemp()) + getResources().getString(R.string.degree));
            }
            else if(mode == Relay.TIME_MODE){
                mMode.setImageResource(R.drawable.ic_time);
                mFirstParam.setText(Integer.toString(mRelay.getPeriodTime()) +" "+  getResources().getString(R.string.minutes));
                mSecondParam.setText(Integer.toString(mRelay.getDurationTime()) +" "+  getResources().getString(R.string.minutes));
            }
            else{
                mMode.setImageResource(R.drawable.ic_hand);
                mFirstParam.setText("");
                mSecondParam.setText("");
            }

            if(mState.get(mRelay.getNumber()).equals("1")){
                mButtonState.setChecked(true);
            }
            else{
                mButtonState.setChecked(false);
            }
            mButtonState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mMode.setImageResource(R.drawable.ic_hand);
                    mRelay.setMode(Relay.HAND_MODE);
                    Model.updateRelay(getActivity(), mRelay);
                    mFirstParam.setText("");
                    mSecondParam.setText("");

                    if(mButtonState.isChecked()){
                        mState.set(mRelay.getNumber(),"1");
                        relayOnRequest(mRelay.getNumber());
                    }
                    else {
                        mState.set(mRelay.getNumber(),"0");
                        relayOffRequest(mRelay.getNumber());
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            Intent intent = RelaySettingsActivity.NewIntent(getActivity(), mRelay.getId());
            startActivity(intent);
        }

    }


    private class RelayAdapter extends RecyclerView.Adapter<RelayHolder>{
        private List<Relay> mRelays;

        public RelayAdapter(List<Relay> relays) {
            mRelays = relays;
        }

        @Override
        public RelayHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new RelayHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(RelayHolder holder, int position) {
            Relay relay = mRelays.get(position);
            holder.bind(relay);
        }

        @Override
        public int getItemCount() {
            return mRelays.size();
        }
        public void setRelays(List<Relay> relays) {
            mRelays = relays;
        }
    }

    @Override
    public void handleTickEvent() {
        updateUI();
    }

    @Override
    public void periodicRequest() {
        relayStateRequest();
    }

    public void relayOnRequest(int num){
        Call<ResponseBody> relayOnReq = Requests.getApi().relayOn(num);
        relayOnReq.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void relayOffRequest(int num){
        Call<ResponseBody> relayOnReq = Requests.getApi().relayOff(num);
        relayOnReq.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void relayStateRequest(){
        Call<ResponseBody> stateReq = Requests.getApi().relayStateList();
        stateReq.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.message().equals("OK")) {
                    try {
                        mState = Arrays.asList(response.body().string().split("/"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        stopProcess();
    }

}
