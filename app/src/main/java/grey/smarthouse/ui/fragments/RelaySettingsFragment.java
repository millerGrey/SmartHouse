package grey.smarthouse.ui.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import grey.smarthouse.model.Model;
import grey.smarthouse.model.Relay;
import grey.smarthouse.R;
import grey.smarthouse.retrofit.Requests;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.Arrays;
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
    private ImageView mTempIcon;
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
//        mRelay = RelayList.getInstance(getActivity()).getRelay(relayId);
        mRelay = Model.getRelay(relayId);
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
        mDescriptionField.setText(mRelay.getDescription());
        mTopTemp = (EditText) v.findViewById(R.id.topTemp);
        mBotTemp = (EditText) v.findViewById(R.id.botTemp);
        mPeriodTime = (EditText) v.findViewById(R.id.periodTime);
        mDurationTime = (EditText) v.findViewById(R.id.durationTime);
        mSaveButton = (Button) v.findViewById(R.id.saveButton);
        mTempIcon = (ImageView) v.findViewById((R.id.ic_temp));

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
                setSettingsToRelay();
                updateUI(mRelay.getMode());
            }
        });

        updateUI(mRelay.getMode());

        return v;
    }

    private void updateUI(int mode)
    {
        boolean mTemp = (mode == 0);
        boolean mTime = (mode == 0x01);
        boolean mHand = (mode == 0x02);
        boolean mCool = ((mode == 0x03));

        mHandModeCheckBox.setChecked(mHand);
        mTempModeCheckBox.setChecked(mTemp);
        mTimeModeCheckBox.setChecked(mTime);
        mTopTemp.setEnabled(mTemp);
        mBotTemp.setEnabled(mTemp);
        mPeriodTime.setEnabled(mTime);
        mDurationTime.setEnabled(mTime);
        if(mRelay.getTopTemp()>mRelay.getBotTemp()){
            mTempIcon.setImageResource(R.drawable.ic_sun);
        }else{
            mTempIcon.setImageResource(R.drawable.ic_snow);
        }
    }

    private void setSettingsToRelay(){
        mRelay.setMode(mHandModeCheckBox.isChecked()?2:(mTempModeCheckBox.isChecked()?0:1));
        mRelay.setTopTemp(Integer.parseInt(mTopTemp.getText().toString()));
        mRelay.setBotTemp(Integer.parseInt(mBotTemp.getText().toString()));
        mRelay.setPeriodTime(Integer.parseInt(mPeriodTime.getText().toString()));
        mRelay.setDurationTime(Integer.parseInt(mDurationTime.getText().toString()));
        mRelay.setDescription(mDescriptionField.getText().toString());
        Model.updateRelay(getActivity(), mRelay);
        int mode = mRelay.getMode();
        String modeS = null;
        switch(mode) {
            case Relay.TEMP_MODE:
                modeS = "temp";
                break;
            case Relay.TIME_MODE:
                modeS = "time";
                break;
            case Relay.HAND_MODE:
                modeS = "hand";
                break;
        }
        Call<ResponseBody> tempReq = Requests.getApi().relaySetConfig(mRelay.getNumber(),
                modeS,
                mRelay.getTopTemp(),
                mRelay.getBotTemp(),
                mRelay.getPeriodTime(),
                mRelay.getDurationTime(),
                mRelay.getSensNum());
        Log.d("TCP", ">>> " + tempReq.request().toString());
        tempReq.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.message().equals("OK")) {
                    String resp = null;
                    try {
                        resp = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d("TCP", "<<< " + response.message() +  " " + resp);
                    Toast.makeText(getContext().getApplicationContext(),"Настройки сохранены",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext().getApplicationContext(),"Ошибка сохранения!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext().getApplicationContext(),"Ошибка сохранения!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
