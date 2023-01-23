package com.example.materialdesing.ui.planets

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.materialdesing.App
import com.example.materialdesing.R
import com.example.materialdesing.databinding.FragmentSystemBinding
import com.example.materialdesing.domain.entity.earth.EarthDtoItem
import com.example.materialdesing.domain.repo.EarthRepo
import com.example.materialdesing.domain.repo.MarsRepo
import com.squareup.picasso.Picasso

class SystemFragment : Fragment(R.layout.fragment_system) {

    private var _binding: FragmentSystemBinding? = null
    private val binding get() = _binding!!

    private val app: App get() = requireActivity().application as App

    private val earthRepo: EarthRepo by lazy {
        app.earthRepo
    }

    private val marsRepo: MarsRepo by lazy {
        app.marsRepo
    }

    private val viewModel: PlanetsViewModel by viewModels {
        PlanetsViewModel.Factory(
            earthRepo, marsRepo
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSystemBinding.bind(view)
    }

    private fun setPhotoDto(earthDtoItem: EarthDtoItem) {
        binding.fotoSystemImageView.setImageResource(R.drawable.ic_earth)
        earthDtoItem.url
        if (earthDtoItem.url.isNotBlank()) {
            Picasso.get()
                .load(earthDtoItem.url)
                .fit() // картинка будет размещена по выделенному размеру для нее.
                .placeholder(R.drawable.uploading_images)
                .into(binding.fotoSystemImageView)
            binding.fotoSystemImageView.scaleType =
                ImageView.ScaleType.FIT_CENTER
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
