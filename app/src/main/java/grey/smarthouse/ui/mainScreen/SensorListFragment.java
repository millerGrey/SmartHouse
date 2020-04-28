package grey.smarthouse.ui.mainScreen;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import grey.smarthouse.R;
import grey.smarthouse.services.NetService;
import grey.smarthouse.ui.RefreshFragment;
import grey.smarthouse.ui.SensorDialog;


/**
 * Created by GREY on 26.05.2018.
 */

public class SensorListFragment extends RefreshFragment {
    public static final String ARG_PAGE = "ARG PAGE";

    private RecyclerView mSensorsRecyclerView;
    private SensorAdapter mSensorAdapter;
    LinearLayout mProgress;
    TextView mProgressText;
    List<String> mTemp = new ArrayList<String>();
    private int mPage;
    private int cnt = 0;
    DialogFragment dialog;

    public static SensorListFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        SensorListFragment fragment = new SensorListFragment();
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
        View view = inflater.inflate(R.layout.fragment_sensor_list, container, false);
        mProgress =  view.findViewById(R.id.progressLayout);
        mProgressText = view.findViewById(R.id.textProgressMessage);
        mSensorsRecyclerView = view.findViewById(R.id.sensor_recycler_view);
        mSensorsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dialog = new SensorDialog();
        mSensorAdapter = new SensorAdapter(mTemp);
        mSensorsRecyclerView.setAdapter(mSensorAdapter);
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
            if(cnt > 3) {
                mProgressText.setText(R.string.checkAdress);
            } else {
                mProgressText.setText(R.string.connection);
            }
            cnt++;
            return;
        } else {
            mProgress.setVisibility(View.INVISIBLE);
        }
        cnt =0;
        mSensorAdapter.setSensors(mTemp);
        mSensorAdapter.notifyDataSetChanged();
    }


    public class SensorHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mValue;

        public SensorHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_sensor, parent, false));
            mValue = itemView.findViewById(R.id.text_sensorValue);
            itemView.setOnClickListener(this);
        }

        public void bind(String value) {
            mValue.setText(value + " °С");
        }


        @Override
        public void onClick(View v) {

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
