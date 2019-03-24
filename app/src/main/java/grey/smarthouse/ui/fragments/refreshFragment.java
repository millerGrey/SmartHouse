package grey.smarthouse.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import grey.smarthouse.Refreshable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by GREY on 11.06.2018.
 */

public abstract class refreshFragment extends Fragment implements Runnable, Refreshable {

    Observable s, s1;
    private Handler responseHandler = new Handler();
    private Thread responseThread;
//    private int mResponseMs;
    private boolean mStop=true;
//    Runnable response = new Runnable(){
//        @Override
//        public void run() {
//            periodicRequest();
//            responseHandler.postDelayed(this,mResponseMs);
//        }
//    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        responseThread = new Thread(new Runnable(){
//            @Override
//            public void run() {
//                responseHandler.postDelayed(response,0);
//            }
//        });
//        responseThread.start();
        s = Observable.interval(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        s.subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("RX","sub");
            }

            @Override
            public void onNext(Long aLong) {
                handleTickEvent();
            }

            @Override
            public void onError(Throwable e) {
                Log.d("RX","onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.d("RX","onC: " );
            }
        });

        s1 = Observable.interval(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        s1.subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("RX","sub");
            }

            @Override
            public void onNext(Long aLong) {
                periodicRequest();
                Log.d("RX","req");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("RX","onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.d("RX","onC: " );
            }
        });
    }

    @Override
    public void run() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (!isVisibleToUser) {
                stopProcess();
            }else{
//                startProcess(mResponseMs);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public void stopProcess()
    {
        mStop = true;
    }

    public void startProcess(int responseDelayMs)
    {
//        mResponseMs = responseDelayMs;

        mStop = false;
//        responseHandler.postDelayed(response,0);

    }
}
