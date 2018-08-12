package com.example.grey.smarthouse;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by GREY on 11.06.2018.
 */

public abstract class refreshFragment extends Fragment implements Runnable, Refreshable {

    private Handler refreshHandler = new Handler();
    private Handler responseHandler = new Handler();
    private Thread responseThread;
    Runnable response = new Runnable(){
        @Override
        public void run() {
            periodicRequest();
            responseHandler.postDelayed(this,5000);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        responseThread = new Thread(new Runnable(){
            @Override
            public void run() {
                responseHandler.postDelayed(response,0);
            }
        });
        responseThread.start();
        refreshHandler.postDelayed(this, 0);
    }

    @Override
    public void run() {
        handleTickEvent();
        refreshHandler.postDelayed(this, 5000);
    }


}
