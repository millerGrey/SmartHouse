package grey.smarthouse.ui.mainScreen;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import grey.smarthouse.R;
import grey.smarthouse.model.App;
import grey.smarthouse.retrofit.Requests;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;




/**
 * Created by GREY on 11.08.2018.
 */

public class SettingsFragment extends Fragment implements TextView.OnEditorActionListener {

    private EditText mEditURL;
    private EditText mEditIP;
    private EditText mEditNotifTemp;
    private Button mReset;
    private SwitchCompat mNotifOn;
    private SwitchCompat mTestSet;

    boolean lastVisibleState = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        mEditURL = (EditText) v.findViewById(R.id.URL);
        mEditURL.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i==6) {
                    App.getApp().mDeviceURL = mEditURL.getText().toString();
                    Requests.RetrofitInit(App.getApp().mDeviceURL);
                    return false;
                }
                return false;
            }
        });
        mEditURL.setText(App.getApp().mDeviceURL);

        mNotifOn = (SwitchCompat) v.findViewById(R.id.switchNotif);
        mNotifOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                App.getApp().mIsNotifOn = b;
            }
        });
        mNotifOn.setChecked(App.getApp().mIsNotifOn);

        mEditNotifTemp = (EditText) v.findViewById(R.id.tempNotif);
        mEditNotifTemp.setOnEditorActionListener(this);
        mEditNotifTemp.setText(Integer.toString(App.getApp().mNotifTemp));
        mTestSet = (SwitchCompat)v.findViewById(R.id.switchTestSet);
        mTestSet.setChecked(App.getApp().mIsTestSet);
        mTestSet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                App.getApp().mIsTestSet = b;
                if (App.getApp().mIsTestSet) {
                    mEditIP.setVisibility(View.VISIBLE);
                    mReset.setVisibility(View.VISIBLE);
                } else {
                    mEditIP.setVisibility(View.INVISIBLE);
                    mReset.setVisibility(View.INVISIBLE);
                }
            }
        });


        mEditIP = (EditText) v.findViewById(R.id.IP);

        mEditIP.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i==6) {
                    Call<ResponseBody> tempReq = Requests.getApi().setIP(mEditIP.getText().toString());
                    Log.d("TCP", ">>> " + tempReq.request().toString());
                    tempReq.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.message().equals("OK")) {
                                Log.d("TCP", "<<< " + response.message());
                                Toast.makeText(getContext().getApplicationContext(), "Настройки сохранены", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getContext().getApplicationContext(),"Ошибка сохранения!",Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getContext().getApplicationContext(),"Ошибка сохранения!",Toast.LENGTH_SHORT).show();
                            t.printStackTrace();
                        }
                    });
                    return false;
                }
                return false;
            }
        });
        mReset = (Button) v.findViewById(R.id.reset);
        if (App.getApp().mIsTestSet) {
            mEditIP.setVisibility(View.VISIBLE);
            mReset.setVisibility(View.VISIBLE);
        } else {
            mEditIP.setVisibility(View.INVISIBLE);
            mReset.setVisibility(View.INVISIBLE);
        }
        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<ResponseBody> tempReq = Requests.getApi().reset();
                Log.d("TCP", ">>> " + tempReq.request().toString());

                tempReq.enqueue(new Callback<ResponseBody>() {
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
        });
        return v;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId==6) {
            App.getApp().mNotifTemp = Integer.parseInt(mEditNotifTemp.getText().toString());
            return false;
        }
        return false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            lastVisibleState = true;
        }else{
            if(lastVisibleState && !isVisibleToUser){
                App.getApp().savePref();
            }
            lastVisibleState = false;
        }

    }


}
