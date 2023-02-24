package com.example.materialdesing.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.materialdesing.databinding.FragmentSettingsBinding
import com.example.materialdesing.ui.ViewBindingFragment

class SettingsFragment : ViewBindingFragment<FragmentSettingsBinding>(
    FragmentSettingsBinding::inflate
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.aboutAppButton.setOnClickListener {
//            Handler(Looper.getMainLooper()).postDelayed({
            getController().openAboutApp()
//            },3_000)
        }

        binding.personalizationAppStylesButton.setOnClickListener {
            getController().openPersonalizationAppStyles()
        }
    }

    interface Controller {
        fun openAboutApp()
        fun openPersonalizationAppStyles()
    }

    private fun getController(): Controller = activity as Controller

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getController()
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }
}