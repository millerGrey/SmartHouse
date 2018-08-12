package com.example.grey.smarthouse;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.grey.smarthouse.Model.RelayList;
import com.example.grey.smarthouse.Retrofit.Requests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by GREY on 26.05.2018.
 */

public class MainFragment extends refreshFragment {
    public static final String ARG_PAGE = "ARG PAGE";
    List<String> mTemp;

    TextView mTempText1;
    TextView mTempText2;
    TextView mTempText3;

    private int mPage;

    public static MainFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mPage = getArguments().getInt(ARG_PAGE);
        }
    }

    @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mTempText1 = (TextView) view.findViewById(R.id.sensorTempText1);
        mTempText2 = (TextView) view.findViewById(R.id.sensorTempText2);
        mTempText3 = (TextView) view.findViewById(R.id.sensorTempText3);

        init();
        return view;
    }

    public void init()
    {
        mTemp = new ArrayList<String>();

        for(int i=0;i<3;i++){
            mTemp.add(i,"0");
        }
        mTempText1.setText("0 °С");
        mTempText2.setText("0 °С");
        mTempText3.setText("0 °С");

    }
    @Override
    public void handleTickEvent(){
        Log.i("HANDLER", "обновление UI");
        try {

            mTempText1.setText(mTemp.get(0) + " °С");
            mTempText2.setText(mTemp.get(1) + " °С");
            mTempText3.setText(mTemp.get(2) + " °С");

        }catch(Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public void periodicRequest(){
        Call<ResponseBody> tempReq = Requests.getApi().ds18b20tempList();
        Log.i("REQEST","запрос");
        tempReq.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.message().equals("OK")) {
                    try {
                        mTemp = Arrays.asList(response.body().string().split("/"));
                        Log.i("REPLY","получен ответ");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }


}
