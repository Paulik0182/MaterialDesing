package com.example.materialdesing.ui.planets

import android.widget.ImageView
import androidx.fragment.app.viewModels
import com.example.materialdesing.App
import com.example.materialdesing.R
import com.example.materialdesing.databinding.FragmentSystemBinding
import com.example.materialdesing.domain.entity.earth.EarthDtoItem
import com.example.materialdesing.domain.repo.EarthRepo
import com.example.materialdesing.domain.repo.MarsRepo
import com.example.materialdesing.ui.ViewBindingFragment
import com.squareup.picasso.Picasso

class SystemFragment : ViewBindingFragment<FragmentSystemBinding>(
    FragmentSystemBinding::inflate
) {

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

    private fun setPhotoDto(earthDtoItem: EarthDtoItem) {
        binding.fotoSystemImageView.setImageResource(R.drawable.ic_earth)
        earthDtoItem.url
        if (earthDtoItem.url?.isNotBlank() == true) {
            Picasso.get()
                .load(earthDtoItem.url)
                .fit() // картинка будет размещена по выделенному размеру для нее.
                .placeholder(R.drawable.uploading_images)
                .into(binding.fotoSystemImageView)
            binding.fotoSystemImageView.scaleType =
                ImageView.ScaleType.FIT_CENTER
        }
    }
}
