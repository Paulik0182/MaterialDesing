package com.example.materialdesing.ui.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    // СПИСОК ФРАГМЕНТОВ
    private val fragments = arrayOf(
        EarthFragment(),
        MarsFragment(),
        SystemFragment()
    )

    override fun createFragment(position: Int): Fragment {
        // позиции фрагментов (размер предопеделен размером списка фрагмента)
        return when (position) {
            0 -> fragments[EARTH_FRAGMENT]
            1 -> fragments[MARS_FRAGMENT]
            2 -> fragments[SYSTEM_FRAGMENT]
            else -> fragments[EARTH_FRAGMENT]
        }
    }

    // возвращаем размер списка (предопеделено размером списка фрагментов)
    override fun getItemCount() = fragments.size

    companion object {
        private const val EARTH_FRAGMENT = 0
        private const val MARS_FRAGMENT = 1
        private const val SYSTEM_FRAGMENT = 2
    }
}
