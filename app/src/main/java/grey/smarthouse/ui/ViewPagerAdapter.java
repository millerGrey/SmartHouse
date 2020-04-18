package grey.smarthouse.ui;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import android.util.Log;

import grey.smarthouse.ui.fragments.MainFragment;
import grey.smarthouse.ui.fragments.RelayListFragment;
import grey.smarthouse.ui.fragments.SettingsFragment;

/**
 * Created by GREY on 26.05.2018.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Датчики", "Устройства", "Настройки" };
    private Context context;

    public ViewPagerAdapter(FragmentManager fm/*, Context context*/) {
        super(fm);
//        this.context = context;
    }

    @Override public int getCount() {
        return PAGE_COUNT;
    }

    @Override public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                Log.i("PAGER","1");
                return MainFragment.newInstance(position);
            case 1:
                Log.i("PAGER","2");
//                RelayListFragment.stopProcess();
                return new RelayListFragment();
            case 2:
                Log.i("PAGER","3");
                return new SettingsFragment();
        }
        return null;
    }

    @Override public CharSequence getPageTitle(int position) {
        // генерируем заголовок в зависимости от позиции
        return tabTitles[position];
    }
}
