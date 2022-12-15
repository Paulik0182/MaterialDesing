package com.example.materialdesing.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.materialdesing.R
import com.example.materialdesing.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSettingsBinding.bind(view)
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}