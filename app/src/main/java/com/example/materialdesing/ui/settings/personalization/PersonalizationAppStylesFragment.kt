package com.example.materialdesing.ui.settings.personalization

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.materialdesing.R
import com.example.materialdesing.databinding.FragmentPersonalizationAppStylesBinding

class PersonalizationAppStylesFragment : Fragment(R.layout.fragment_personalization_app_styles) {

    private var _binding: FragmentPersonalizationAppStylesBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentPersonalizationAppStylesBinding.bind(view)

        clickThemeSwitchLightDark()
    }

    private fun clickThemeSwitchLightDark() {
        binding.switchLightDarkThemes.isChecked =
            AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        binding.switchLightDarkThemes.setOnCheckedChangeListener { _, isChecked ->
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
            )
        }
    }

    interface Controller {
        // todo
    }

    private fun getController(): Controller = activity as Controller

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getController()
    }

    companion object {
        @JvmStatic
        fun newInstance() = PersonalizationAppStylesFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}