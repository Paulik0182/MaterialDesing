package com.example.materialdesing.ui.navigation.earth

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.materialdesing.R
import com.example.materialdesing.databinding.FragmentEarthBinding
import com.google.android.material.tabs.TabLayoutMediator

class EarthFragment : Fragment(R.layout.fragment_earth) {

    private var _binding: FragmentEarthBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentEarthBinding.bind(view)

        binding.viewPager.adapter = ViewPagerEarthAdapter(this)
        setTabs()

    }

    private fun setTabs() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                TODAY_EARTH -> {
                    tab.text = getText(R.string.today).toString()
                    tab.icon = ContextCompat.getDrawable(requireContext(), R.drawable.earth)
//                    binding.todayPaginationImageView.setImageResource(R.drawable.swipe_indicator_active)
                }
                YESTERDAY_EARTH -> {
                    tab.text = getText(R.string.yesterday).toString()
                    tab.icon = ContextCompat.getDrawable(requireContext(), R.drawable.earth)
//                    binding.yesterdayPaginationImageView.setImageResource(R.drawable.swipe_indicator_active)

                }
                TWO_DATS_AGO_EARTH -> {
                    tab.text = getText(R.string.day_before_yesterday).toString()
                    tab.icon = ContextCompat.getDrawable(requireContext(), R.drawable.earth)
//                    binding.dayBeforeYesterdayPaginationImageView.setImageResource(R.drawable.swipe_indicator_active)

                }
                else -> {
                    tab.text = getText(R.string.today).toString()
                    tab.icon = ContextCompat.getDrawable(requireContext(), R.drawable.earth)
//                    binding.todayPaginationImageView.setImageResource(R.drawable.swipe_indicator_active)

                }
            }
        }.attach() // вызов медиатора
    }

    companion object {
        private const val TODAY_EARTH = 0
        private const val YESTERDAY_EARTH = 1
        private const val TWO_DATS_AGO_EARTH = 2
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

