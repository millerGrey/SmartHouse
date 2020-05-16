package grey.smarthouse.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by GREY on 11.06.2018.
 */

abstract class RefreshFragment : Fragment(), Refreshable {

    internal lateinit var refresh: Observable<Long>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        refresh = Observable.interval(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        refresh.subscribe({ x -> handleTickEvent() },
                { e -> e.printStackTrace() },
                { Log.d("RX", "complete") },
                { d -> Log.d("RX", "sub") })
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
