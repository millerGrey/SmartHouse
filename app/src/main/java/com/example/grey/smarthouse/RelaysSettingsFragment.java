package com.example.grey.smarthouse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.UUID;

/**
 * Created by GREY on 29.04.2018.
 */

public class RelaysSettingsFragment extends Fragment {

    private static final String ARG_RELAY_ID = "relay_id";


    Relay mRelay;
    private EditText mDescriptionField;
    private EditText mTopTemp;
    private EditText mBotTemp;
    private EditText mPeriodTime;
    private EditText mDurationTime;
    //    private Text mTextTopTemp;
//    private Text mTextBotTemp;
//    private Text mTextPeriod;
//    private Text mTextDuration;
    private CheckBox mHandModeCheckBox;
    private CheckBox mTempModeCheckBox;
    private CheckBox mTimeModeCheckBox;


    public static RelaysSettingsFragment newInstance(UUID rId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_RELAY_ID, rId);
        RelaysSettingsFragment fragment = new RelaysSettingsFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_relays_settings, container, false);

        mDescriptionField = (EditText) v.findViewById(R.id.description);
        mTopTemp = (EditText) v.findViewById(R.id.topTemp);
        mBotTemp = (EditText) v.findViewById(R.id.botTemp);
        mPeriodTime = (EditText) v.findViewById(R.id.periodTime);
        mDurationTime = (EditText) v.findViewById(R.id.durationTime);

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
