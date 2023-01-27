package com.example.materialdesing.ui.planets.earth

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerEarthAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    // СПИСОК ФРАГМЕНТОВ
    val fragments = arrayOf(
        FotoEarthFragment.newInstance(),
        FotoEarthFragment(),
        FotoEarthFragment()
    )

    override fun createFragment(position: Int): Fragment {
        // позиции фрагментов (размер предопеделен размером списка фрагмента)
        return when (position) {
            0 -> fragments[TODAY_EARTH]
            1 -> fragments[YESTERDAY_EARTH]
            2 -> fragments[TWO_DATS_AGO_EARTH]
            else -> fragments[TODAY_EARTH]
        }
    }

    // возвращаем размер списка (предопеделено размером списка фрагментов)
    override fun getItemCount() = fragments.size

    companion object {
        private const val TODAY_EARTH = 0
        private const val YESTERDAY_EARTH = 1
        private const val TWO_DATS_AGO_EARTH = 2
    }
}
