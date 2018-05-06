package com.example.grey.smarthouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

/**
 * Created by GREY on 30.04.2018.
 */

public class RelayListFragment extends Fragment {

    private RecyclerView mRelayRecyclerView;
    private RelayAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_relay_list, container, false);
        mRelayRecyclerView = (RecyclerView)v.findViewById(R.id.relay_recycler_view);
        mRelayRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return v;
    }


    private void updateUI(){
        RelayList relayList = RelayList.getInstance(getActivity());
        List<Relay> relays = relayList.getRelays();

        if(mAdapter == null){
            mAdapter = new RelayAdapter(relays);
            mRelayRecyclerView.setAdapter(mAdapter);
        }
        else
        {
            mAdapter.notifyDataSetChanged();
        }

    }

    public class RelayHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Relay mRelay;
        private TextView mDescription;
        private TextView mMode;
        private TextView mFirstParam;
        private TextView mSecondParam;


        public RelayHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_relay,parent, false));
            mDescription = itemView.findViewById(R.id.item_description);
            mMode = itemView.findViewById(R.id.item_mode);
            mFirstParam = itemView.findViewById(R.id.item_first_param);
            mSecondParam = itemView.findViewById(R.id.item_second_param);
            itemView.setOnClickListener(this);
        }

        public void bind(Relay relay){
            mRelay = relay;
            int mode = mRelay.getMode();
            mDescription.setText(mRelay.getDescription());

            if(mode == Relay.TEMP_MODE){
                mMode.setText(R.string.temp_mode);
                mFirstParam.setText(Integer.toString(mRelay.getTopTemp()));
                mSecondParam.setText(Integer.toString(mRelay.getBotTemp()));
            }
            else if(mode == Relay.TIME_MODE){
                mMode.setText(R.string.time_mode);
                mFirstParam.setText(Integer.toString(mRelay.getPeriodTime()));
                mSecondParam.setText(Integer.toString(mRelay.getDurationTime()));
            }
            else{
                mMode.setText(R.string.hand_mode);
                mFirstParam.setText("");
                mFirstParam.setText("");
            }
        }

        @Override
        public void onClick(View v) {
            Intent intent = RelaySetActivity.NewIntent(getActivity(), mRelay.getId());
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
    }

}
