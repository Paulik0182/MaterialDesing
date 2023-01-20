package com.example.materialdesing.ui.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.materialdesing.R
import com.example.materialdesing.databinding.ActivityBarViewBinding
import com.example.materialdesing.ui.navigation.earth.EarthFragment

private const val TAG_BAR_VIEW_ACTIVITY_CONTAINER_LAYOUT_KEY =
    "TAG_BAR_VIEW_ACTIVITY_CONTAINER_LAYOUT_KEY"

class BarViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBarViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBarViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBottomNavBar()

        if (savedInstanceState == null) {
            binding.bottomNavPlanetsBar.selectedItemId = R.id.action_view_earth_item
        } else {
            // todo что-то другое
        }
    }

    private fun onBottomNavBar() {
        binding.bottomNavPlanetsBar.setOnItemSelectedListener {
            title = it.title
            when (it.itemId) {
                R.id.action_view_earth_item -> {
                    swapFragment(EarthFragment())
                    true
                }
                R.id.action_view_mars_item -> {
                    swapFragment(MarsFragment())
                    false
                }
                R.id.action_view_system_item -> {
                    swapFragment(SystemFragment())
                }
                R.id.exit_item -> {
                    finish()
                    false
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun swapFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                binding.container.id,
                fragment,
                TAG_BAR_VIEW_ACTIVITY_CONTAINER_LAYOUT_KEY
            ).commit()
    }
}
