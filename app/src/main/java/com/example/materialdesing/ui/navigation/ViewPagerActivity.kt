package com.example.materialdesing.ui.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.materialdesing.R
import com.example.materialdesing.databinding.ActivityViewPagerBinding
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerActivity : AppCompatActivity() {


    private lateinit var binding: ActivityViewPagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = ViewPagerAdapter(this)
        setTabs()
    }

    private fun setTabs() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                EARTH -> {
                    getString(R.string.earth_tab_text)
                }
                MARS -> {
                    getString(R.string.mars_tab_text)
                }
                SYSTEM -> {
                    getString(R.string.weather_tab_text)
                }
                else -> {
                    getString(R.string.earth_tab_text)
                }
            }

            tab.icon = when (position) {
                EARTH -> {
                    ContextCompat.getDrawable(this@ViewPagerActivity, R.drawable.earth)
                }
                MARS -> {
                    ContextCompat.getDrawable(this@ViewPagerActivity, R.drawable.mars)
                }
                SYSTEM -> {
                    ContextCompat.getDrawable(this@ViewPagerActivity, R.drawable.system)
                }
                else -> {
                    ContextCompat.getDrawable(this@ViewPagerActivity, R.drawable.earth)
                }
            }
        }.attach() // вызов медиатора
    }

    companion object {
        private const val EARTH = 0
        private const val MARS = 1
        private const val SYSTEM = 2
    }
}
