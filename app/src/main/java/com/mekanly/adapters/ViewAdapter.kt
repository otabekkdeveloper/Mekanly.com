import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mekanly.FiveFragment
import com.mekanly.FlowFragment
import com.mekanly.FourthFragment
import com.mekanly.SearchFragment
import com.mekanly.ThirdFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 5 // Количество вкладок
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FlowFragment()   // Раздел "Baş sahypa"
            1 -> SearchFragment()  // Раздел "Emlaklar"
            2 -> ThirdFragment() // Раздел "Satylanlar"
            3 -> FourthFragment() // Раздел "Hazirlanýan"
            4 -> FiveFragment()  // Раздел "Menyu"
            else -> FlowFragment()
        }
    }
}
