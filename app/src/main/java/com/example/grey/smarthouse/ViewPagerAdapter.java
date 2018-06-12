package com.example.grey.smarthouse;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.grey.smarthouse.MainFragment;
import com.example.grey.smarthouse.RelayListFragment;

/**
 * Created by GREY on 26.05.2018.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Tab1", "Настройки", "Tab3" };
    private Context context;

    public ViewPagerAdapter(FragmentManager fm/*, Context context*/) {
        super(fm);
//        this.context = context;
    }

    @Override public int getCount() {
        return PAGE_COUNT;
    }

    @Override public Fragment getItem(int position) {
        switch(position+1)
        {
            case 1:
                Log.i("PAGER","1");
                return MainFragment.newInstance(position);
            case 2:
                Log.i("PAGER","2");
                return new RelayListFragment();
            case 3:
                Log.i("PAGER","3");
                return new RelayListFragment();
        }
        return null;
    }

    @Override public CharSequence getPageTitle(int position) {
        // генерируем заголовок в зависимости от позиции
        return tabTitles[position];
    }
}
