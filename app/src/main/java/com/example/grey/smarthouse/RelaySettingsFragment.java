package com.example.grey.smarthouse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.grey.smarthouse.Model.Relay;
import com.example.grey.smarthouse.Model.RelayList;

import java.util.UUID;

/**
 * Created by GREY on 29.04.2018.
 */

public class RelaySettingsFragment extends Fragment {

    private static final String ARG_RELAY_ID = "relay_id";


    Relay mRelay;
    private TextView mNumber;
    private EditText mDescriptionField;
    private EditText mTopTemp;
    private EditText mBotTemp;
    private EditText mPeriodTime;
    private EditText mDurationTime;
    private CheckBox mHandModeCheckBox;
    private CheckBox mTempModeCheckBox;
    private CheckBox mTimeModeCheckBox;
    private Button mSaveButton;


    public static RelaySettingsFragment newInstance(UUID rId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_RELAY_ID, rId);
        RelaySettingsFragment fragment = new RelaySettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID relayId = (UUID) getArguments().getSerializable(ARG_RELAY_ID);
        mRelay = RelayList.getInstance(getActivity()).getRelay(relayId);

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI(mRelay.getMode());
        mTopTemp.setText(String.valueOf(mRelay.getTopTemp()));
        mBotTemp.setText(String.valueOf(mRelay.getBotTemp()));
        mPeriodTime.setText(String.valueOf(mRelay.getPeriodTime()));
        mDurationTime.setText(String.valueOf(mRelay.getDurationTime()));
        mDescriptionField.setText(mRelay.getDescription());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_relay_set, container, false);

        mNumber = (TextView) v.findViewById(R.id.relayNumber);
        mNumber.setText(String.format(getResources().getString(R.string.relay_n), mRelay.getNumber()));
        mDescriptionField = (EditText) v.findViewById(R.id.description);
        mDescriptionField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mRelay.setDescription(s.toString());
            }
        });
        mTopTemp = (EditText) v.findViewById(R.id.topTemp);
        mBotTemp = (EditText) v.findViewById(R.id.botTemp);
        mPeriodTime = (EditText) v.findViewById(R.id.periodTime);
        mDurationTime = (EditText) v.findViewById(R.id.durationTime);
        mSaveButton = (Button) v.findViewById(R.id.saveButton);

        mHandModeCheckBox = (CheckBox) v.findViewById(R.id.handModeCheckbox);
        mHandModeCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRelay.setMode(Relay.HAND_MODE);
                updateUI(mRelay.getMode());
            }
        });
        mTempModeCheckBox = (CheckBox) v.findViewById(R.id.tempModeCheckbox);
        mTempModeCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRelay.setMode(Relay.TEMP_MODE);
                updateUI(mRelay.getMode());
            }
        });
        mTimeModeCheckBox = (CheckBox) v.findViewById(R.id.timeModeCheckbox);
        mTimeModeCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRelay.setMode(Relay.TIME_MODE);
                updateUI(mRelay.getMode());
            }
        });
        mSaveButton = (Button) v.findViewById(R.id.saveButton);
        mSaveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                RelayList.getInstance(getActivity()).updateRelay(mRelay);
            }
        });

        updateUI(mRelay.getMode());

        return v;
    }

    private void updateUI(int mode)
    {
        boolean mTemp = (mode == 0);
        boolean mTime = ((mode & 0x01)!=0);
        boolean mHand = ((mode & 0x02)!=0);

        mHandModeCheckBox.setChecked(mHand);
        mTempModeCheckBox.setChecked(mTemp);
        mTimeModeCheckBox.setChecked(mTime);
        mTopTemp.setEnabled(mTemp);
        mBotTemp.setEnabled(mTemp);
        mPeriodTime.setEnabled(mTime);
        mDurationTime.setEnabled(mTime);
    }

}
