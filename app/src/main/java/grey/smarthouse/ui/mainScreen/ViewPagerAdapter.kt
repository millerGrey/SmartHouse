package grey.smarthouse.ui.mainScreen

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * Created by GREY on 26.05.2018.
 */

class ViewPagerAdapter(fm: FragmentManager/*, Context context*/)//        this.context = context;
    : FragmentStatePagerAdapter(fm) {

    internal val PAGE_COUNT = 3
    private val tabTitles = arrayOf("Датчики", "Устройства", "Настройки")

    override fun getCount(): Int {
        return PAGE_COUNT
    }

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> SensorListFragment.newInstance(position)

            1 -> RelayListFragment()

            2 -> SettingsFragment()
            else -> null
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        // генерируем заголовок в зависимости от позиции
        return tabTitles[position]
    }
}
