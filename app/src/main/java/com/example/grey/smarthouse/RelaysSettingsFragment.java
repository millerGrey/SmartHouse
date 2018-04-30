package com.example.grey.smarthouse;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import org.w3c.dom.Text;

import java.util.UUID;

/**
 * Created by GREY on 29.04.2018.
 */

public class RelaysSettingsFragment extends Fragment {

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


    public static RelaysSettingsFragment newInstance() {
        Bundle args = new Bundle();
        //args.putSerializable(ARG_CRIME_ID, crimeId);
        RelaysSettingsFragment fragment = new RelaysSettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_relays_settings, container, false);

        mDescriptionField = (EditText) v.findViewById(R.id.description);
        mTopTemp = (EditText) v.findViewById(R.id.topTemp);
        mBotTemp = (EditText) v.findViewById(R.id.botTemp);
        mPeriodTime = (EditText) v.findViewById(R.id.periodTime);
        mDurationTime = (EditText) v.findViewById(R.id.durationTime);

        mHandModeCheckBox = (CheckBox)v.findViewById(R.id.handModeCheckbox);
        mHandModeCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandModeCheckBox.setChecked(true);
                mTempModeCheckBox.setChecked(false);
                mTimeModeCheckBox.setChecked(false);
                mTopTemp.setEnabled(false);
                mBotTemp.setEnabled(false);
                mPeriodTime.setEnabled(false);
                mDurationTime.setEnabled(false);
            }
        });
        mTempModeCheckBox = (CheckBox)v.findViewById(R.id.tempModeCheckbox);
        mTempModeCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTempModeCheckBox.setChecked(true);
                mHandModeCheckBox.setChecked(false);
                mTimeModeCheckBox.setChecked(false);
                mTopTemp.setEnabled(true);
                mBotTemp.setEnabled(true);
                mPeriodTime.setEnabled(false);
                mDurationTime.setEnabled(false);
            }
        });
        mTimeModeCheckBox = (CheckBox)v.findViewById(R.id.timeModeCheckbox);
        mTimeModeCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimeModeCheckBox.setChecked(true);
                mHandModeCheckBox.setChecked(false);
                mTempModeCheckBox.setChecked(false);
                mTopTemp.setEnabled(false);
                mBotTemp.setEnabled(false);
                mPeriodTime.setEnabled(true);
                mDurationTime.setEnabled(true);
            }
        });

        return v;
    }
}
