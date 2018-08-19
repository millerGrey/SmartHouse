package com.example.grey.smarthouse;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import com.example.grey.smarthouse.Retrofit.Requests;

import java.util.UUID;

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
        Requests.RetrofitInit();
    }
}
