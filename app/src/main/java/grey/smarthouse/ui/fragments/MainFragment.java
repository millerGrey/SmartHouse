package grey.smarthouse.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import grey.smarthouse.services.NetService;
import grey.smarthouse.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by GREY on 26.05.2018.
 */

public class MainFragment extends refreshFragment {
    public static final String ARG_PAGE = "ARG PAGE";

    private RecyclerView mSensorsRecyclerView;
    private SensorAdapter mSensorAdapter;
    ProgressBar mProgress;
    List<String> mTemp = new ArrayList<String>();
    private int mPage;
    private int cnt = 0;

    public static MainFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getInt(ARG_PAGE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mProgress = (ProgressBar) view.findViewById(R.id.progress);
        mSensorsRecyclerView = (RecyclerView) view.findViewById(R.id.sensor_recycler_view);
        mSensorsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }


    @Override
    public void handleTickEvent() {
        Log.d("RX", "handletick");
        updateUI();
    }

    public void updateUI() {
        mTemp = NetService.getTemp();
        if (mTemp == null) {
            mProgress.setVisibility(View.VISIBLE);
        } else {
            mProgress.setVisibility(View.INVISIBLE);
        }
        if (mSensorAdapter == null) {
            mSensorAdapter = new SensorAdapter(mTemp);
            mSensorsRecyclerView.setAdapter(mSensorAdapter);
        } else {
            mSensorAdapter.notifyDataSetChanged();
        }
        mSensorAdapter.setSensors(mTemp);
    }


    public class SensorHolder extends RecyclerView.ViewHolder {
        private TextView mValue;

        public SensorHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_sensor, parent, false));
            mValue = itemView.findViewById(R.id.text_sensorValue);
        }

        public void bind(String value) {
            mValue.setText(value + " °С");
        }

    }

    private class SensorAdapter extends RecyclerView.Adapter<SensorHolder> {
        private List<String> mTemp;

        public SensorAdapter(List<String> values) {
            setSensors(values);
        }

        @Override
        public SensorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new SensorHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(SensorHolder holder, int position) {
            String value = mTemp.get(position);
            holder.bind(value);
        }

        @Override
        public int getItemCount() {
            try {
                return mTemp.size();
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }

        public void setSensors(List<String> values) {
            mTemp = values;
        }
    }

}
