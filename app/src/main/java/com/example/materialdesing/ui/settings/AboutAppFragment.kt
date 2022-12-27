package com.example.materialdesing.ui.settings

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.materialdesing.BuildConfig
import com.example.materialdesing.R
import com.example.materialdesing.databinding.FragmentAboutAppBinding

class AboutAppFragment : Fragment(R.layout.fragment_about_app) {

    private var _binding: FragmentAboutAppBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentAboutAppBinding.bind(view)

        informationApp()
    }


    @SuppressLint("SetTextI18n")
    private fun informationApp() {
        binding.codVersionTextView.text = "Код версии: " + BuildConfig.VERSION_CODE
        binding.versionTextView.text = "Версия: " + BuildConfig.VERSION_NAME
        binding.aboutAppTextView.text =
            "О Приложении\n\nПриложение является результатом выполнения " +
                    "практических заданий по освоению разработки Android приложений." +
                    "\nКурс - Material design"
    }

    companion object {

        @JvmStatic
        fun newInstance() = AboutAppFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}