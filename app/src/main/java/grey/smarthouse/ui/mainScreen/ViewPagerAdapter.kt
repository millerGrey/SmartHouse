package grey.smarthouse.ui.mainScreen

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import grey.smarthouse.ui.mainScreen.locations.LocationListFragment
import grey.smarthouse.ui.mainScreen.relays.RelayListFragment
import grey.smarthouse.ui.mainScreen.sensors.SensorListFragment

/**
 * Created by GREY on 26.05.2018.
 */

class ViewPagerAdapter(fm: FragmentManager, behavior: Int/*, Context context*/)//        this.context = context;
    : FragmentStatePagerAdapter(fm,behavior) {

    internal val PAGE_COUNT = 4
    private val tabTitles = arrayOf("Датчики", "Устройства", "Настройки", "Помещения")

    override fun getCount(): Int {
        return PAGE_COUNT
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> SensorListFragment.newInstance(position)

            1 -> RelayListFragment()

            2 -> SettingsFragment()

            3 -> LocationListFragment()
            else -> Fragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        // генерируем заголовок в зависимости от позиции
        return tabTitles[position]
    }
}
