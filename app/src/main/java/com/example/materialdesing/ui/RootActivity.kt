package com.example.materialdesing.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.materialdesing.R
import com.example.materialdesing.databinding.ActivityRootBinding
import com.example.materialdesing.ui.nasa.PhotoDeyFragment
import com.example.materialdesing.ui.settings.AboutAppFragment
import com.example.materialdesing.ui.settings.SettingsFragment

private const val TAG_ROOT_CONTAINER_LAYOUT_KEY = "TAG_ROOT_CONTAINER_LAYOUT_KEY"

class RootActivity : AppCompatActivity(),
    SettingsFragment.Controller {

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
            val fragment = when (it.itemId) {
                R.id.photo_dey_item -> photoDeyFragment
                R.id.settings_item -> SettingsFragment()
                else -> throw IllegalStateException("Нет фрагмента")
            }
            swapFragment(fragment)
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

    override fun openAboutApp() {
        onAboutApp()
    }


}