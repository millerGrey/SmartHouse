package grey.smarthouse.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import java.util.concurrent.TimeUnit;
import grey.smarthouse.ui.Refreshable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by GREY on 11.06.2018.
 */

public abstract class refreshFragment extends Fragment implements Refreshable {

    Observable<Long> refresh;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refresh = Observable.interval(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        refresh.subscribe(x->handleTickEvent(),
                e->e.printStackTrace(),
                ()->Log.d("RX","complete"),
                d->Log.d("RX","sub"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
