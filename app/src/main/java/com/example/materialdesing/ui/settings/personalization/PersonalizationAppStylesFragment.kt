package com.example.materialdesing.ui.settings.personalization

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.transition.TransitionManager
import com.example.materialdesing.R
import com.example.materialdesing.databinding.FragmentPersonalizationAppStylesBinding
import com.google.android.material.chip.Chip

class PersonalizationAppStylesFragment : Fragment(R.layout.fragment_personalization_app_styles) {

    private var _binding: FragmentPersonalizationAppStylesBinding? = null
    private val binding get() = _binding!!
    var isFlag = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentPersonalizationAppStylesBinding.bind(view)

        clickThemeSwitchLightDark()

        clickThemeChipGroup()
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

    private fun clickThemeChipGroup() {
        binding.themeChipGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.pink_theme_chip -> {
                    requireContext().setTheme(R.style.PinkTheme)
                    mixingElementsChipGroup()
                }
                R.id.green_theme_chip -> {
                    requireContext().setTheme(R.style.GreenTheme)
                    mixingElementsChipGroup()
                }
                R.id.white_theme_chip -> {
                    requireContext().setTheme(R.style.WhiteTheme)
                    mixingElementsChipGroup()
                }
            }
        }
    }

    private fun mixingElementsChipGroup() {
        val chipId: List<Int> =
            listOf((R.id.pink_theme_chip), (R.id.green_theme_chip), (R.id.white_theme_chip))

        TransitionManager.beginDelayedTransition(binding.root)
        binding.themeChipGroup.removeAllViews()
        chipId.shuffled().random()
        chipId.forEach {
            binding.themeChipGroup.addView(Chip(requireContext()).apply {
                id = it
                when (id) {
                    chipId[0] -> text = getText(R.string.pink_theme)
                    chipId[1] -> text = getText(R.string.green_theme)
                    chipId[2] -> text = getText(R.string.white_theme)
                }
            })
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