package grey.smarthouse.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import grey.smarthouse.model.Model;
import grey.smarthouse.R;
import grey.smarthouse.retrofit.Requests;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by GREY on 11.08.2018.
 */

public class SettingsFragment extends Fragment implements View.OnClickListener {

    private EditText mURL;
    private EditText mIP;
    private Button mSaveURL;
    private Button mSaveIP;
    private Button mReset;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        mURL = (EditText)v.findViewById(R.id.URL);
        mURL.setText(Model.mDeviceURL);
        mIP = (EditText)v.findViewById(R.id.IP);
        mSaveURL = (Button)v.findViewById(R.id.saveURL);
        mSaveIP = (Button)v.findViewById(R.id.saveIP);
        mReset = (Button)v.findViewById(R.id.reset);
        mSaveURL.setOnClickListener(this);
        mReset.setOnClickListener(new View.OnClickListener(){
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
    public void onClick(View view) {
        Model.mDeviceURL = mURL.getText().toString();
        Requests.RetrofitInit(Model.mDeviceURL);
        Model.savePref(getActivity().getPreferences(MODE_PRIVATE));
    }




}
