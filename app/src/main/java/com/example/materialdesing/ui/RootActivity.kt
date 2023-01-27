package com.example.materialdesing.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.materialdesing.R
import com.example.materialdesing.databinding.ActivityRootBinding
import com.example.materialdesing.ui.nasa.PhotoDeyFragment
import com.example.materialdesing.ui.planets.earth.BarViewActivity
import com.example.materialdesing.ui.settings.AboutAppFragment
import com.example.materialdesing.ui.settings.SettingsFragment
import com.example.materialdesing.ui.settings.personalization.PersonalizationAppStylesFragment

private const val TAG_ROOT_CONTAINER_LAYOUT_KEY = "TAG_ROOT_CONTAINER_LAYOUT_KEY"

class RootActivity : AppCompatActivity(),
    SettingsFragment.Controller,
    PersonalizationAppStylesFragment.Controller {

    private lateinit var binding: ActivityRootBinding

    private val photoDeyFragment: PhotoDeyFragment = PhotoDeyFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBottomNavBar()

        if (savedInstanceState == null) {
            binding.bottomNavBar.selectedItemId = R.id.photo_dey_item
        } else {
            // todo что-то другое
        }
    }

    private fun hidingVisibilityBar() {
        binding.bottomNavBar.visibility = View.GONE
    }

    private fun onBottomNavBar() {
        binding.bottomNavBar.setOnItemSelectedListener {
            title = it.title
            when (it.itemId) {
                R.id.photo_dey_item -> {
                    swapFragment(photoDeyFragment)
                }
                R.id.settings_item -> {
                    swapFragment(SettingsFragment())
                }
                R.id.app_bar_bottom_navigation -> {
                    val intent = Intent(this, BarViewActivity::class.java)
                    startActivity(intent)
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun swapFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                binding.fragmentContainerFrameLayout.id,
                fragment,
                TAG_ROOT_CONTAINER_LAYOUT_KEY
            ).commit()
    }

    private fun onAboutApp() {
        val fragment: Fragment = AboutAppFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainerFrameLayout.id, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun onPersonalizationAppStyles() {
        val fragment: Fragment = PersonalizationAppStylesFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainerFrameLayout.id, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun openAboutApp() {
        onAboutApp()
    }

    override fun openPersonalizationAppStyles() {
        onPersonalizationAppStyles()
    }


}