package grey.smarthouse.ui.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;


import grey.smarthouse.services.NetService;
import grey.smarthouse.R;
import grey.smarthouse.ui.ViewPagerAdapter;
import grey.smarthouse.model.Model;
import grey.smarthouse.retrofit.Requests;
import io.reactivex.Observable;

/**
 * Created by GREY on 26.05.2018.
 */

public class MainActivity extends SingleFragmentActivity {



    private ViewPager mViewPager;

    @Override
    protected Fragment createFragment() {
        //return MainFragment.newInstance(mPage);
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager =findViewById(R.id.view_pager);
        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new ViewPagerAdapter(fm));

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        Requests.RetrofitInit(Model.mDeviceURL);
        startService(new Intent(this,NetService.class));
        Observable<String> observable = Observable.just("one",
                "two", "three");


//       observable.subscribe(x->Log.d("RX","next"),
//               e->e.printStackTrace(),
//               ()->Log.d("RX","complete"),
//               d->Log.d("RX","sub"));


    }
}
