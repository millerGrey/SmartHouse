package grey.smarthouse.ui.mainScreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import grey.smarthouse.R;
import grey.smarthouse.model.Relay;
import grey.smarthouse.model.RelayList;
import grey.smarthouse.retrofit.Requests;
import grey.smarthouse.ui.RefreshFragment;
import grey.smarthouse.ui.relaySettingsScreen.RelaySettingsActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by GREY on 30.04.2018.
 */

public class RelayListFragment extends RefreshFragment {

    private RecyclerView mRelayRecyclerView;
    private RelayAdapter mAdapter;
    private RelayList mRelayList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("tag", "listFragmentCreate");
        mRelayList = RelayList.getInstance();

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_relay_list, container, false);
        mRelayRecyclerView = (RecyclerView) v.findViewById(R.id.relay_recycler_view);
        mRelayRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return v;
    }


    private void updateUI() {
        List<Relay> relays = mRelayList.getRelays();
        if (mAdapter == null) {
            mAdapter = new RelayAdapter(relays);
            mRelayRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
        mAdapter.setRelays(relays);
    }

    public class RelayHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Relay mRelay;
        private TextView mName;
        private ImageView mMode;
        private TextView mFirstParam;
        private TextView mSecondParam;
        private TextView mDescription;
        private ToggleButton mButtonState;

        public RelayHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_relay, parent, false));
            mName = itemView.findViewById(R.id.item_name);
            mDescription = itemView.findViewById(R.id.textDescription);
            mMode = itemView.findViewById(R.id.item_image_mode);
            mFirstParam = itemView.findViewById(R.id.item_first_param);
            mSecondParam = itemView.findViewById(R.id.item_second_param);
            mButtonState = itemView.findViewById(R.id.buttonState);
            itemView.setOnClickListener(this);
        }

        public void bind(Relay relay) {
            mRelay = relay;
            int mode = mRelay.getMode();
            mName.setText("Реле " + mRelay.getNumber());
            mDescription.setText(mRelay.getDescription());
            if (mode == Relay.TEMP_MODE) {
                if ((mRelay.getBotTemp() > mRelay.getTopTemp())) {
                    mMode.setImageResource(R.drawable.ic_snow);
                } else {
                    mMode.setImageResource(R.drawable.ic_sun);
                }
                mFirstParam.setText(Integer.toString(mRelay.getTopTemp()) + getResources().getString(R.string.degree));
                mSecondParam.setText(Integer.toString(mRelay.getBotTemp()) + getResources().getString(R.string.degree));
            } else if (mode == Relay.TIME_MODE) {
                mMode.setImageResource(R.drawable.ic_time);
                mFirstParam.setText(Integer.toString(mRelay.getPeriodTime()) + " " + getResources().getString(R.string.minutes));
                mSecondParam.setText(Integer.toString(mRelay.getDurationTime()) + " " + getResources().getString(R.string.minutes));
            } else {
                mMode.setImageResource(R.drawable.ic_hand);
                mFirstParam.setText("");
                mSecondParam.setText("");
            }

            if (mRelayList.mRelayStates.get(mRelay.getNumber()).equals("1")) {
                mButtonState.setChecked(true);
            } else {
                mButtonState.setChecked(false);
            }
            mButtonState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mMode.setImageResource(R.drawable.ic_hand);
                    mRelay.setMode(Relay.HAND_MODE);
                    mRelayList.updateRelay(mRelay);
                    mFirstParam.setText("");
                    mSecondParam.setText("");

                    if (mButtonState.isChecked()) {
                        mRelayList.mRelayStates.set(mRelay.getNumber(), "1");
                        relayOnRequest(mRelay.getNumber());
                    } else {
                        mRelayList.mRelayStates.set(mRelay.getNumber(), "0");
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


    private class RelayAdapter extends RecyclerView.Adapter<RelayHolder> {
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

    public void relayOnRequest(int num) {
        Call<ResponseBody> relayOnReq = Requests.getApi().relayOn(num);
        Log.d("TCP", ">>> " + relayOnReq.request().toString());
        relayOnReq.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("TCP", "<<< " + response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void relayOffRequest(int num) {
        Call<ResponseBody> relayOffReq = Requests.getApi().relayOff(num);
        Log.d("TCP", ">>> " + relayOffReq.request().toString());
        relayOffReq.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("TCP", "<<< " + response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            List<Relay> relays = mRelayList.getRelays();
            for (Relay r : relays) {
                Requests.relayConfigRequest(r, mRelayList);
            }
        }
    }
}
