package com.example.materialdesing.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.materialdesing.R
import com.example.materialdesing.databinding.FragmentSettingsStartBinding

class SettingsFragment : Fragment(R.layout.fragment_settings_start) {

    private var _binding: FragmentSettingsStartBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSettingsStartBinding.bind(view)

        binding.aboutAppButton.setOnClickListener {
//            getController().openAboutApp()
        }

        binding.personalizationAppStylesButton.setOnClickListener {
//            getController().openPersonalizationAppStyles()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}