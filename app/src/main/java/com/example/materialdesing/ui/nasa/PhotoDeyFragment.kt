package com.example.materialdesing.ui.nasa

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.materialdesing.App
import com.example.materialdesing.R
import com.example.materialdesing.databinding.FragmentPhotoDeyBinding
import com.example.materialdesing.domain.entity.PhotoDayDto
import com.example.materialdesing.domain.repo.PhotoDayDtoRepo
import com.squareup.picasso.Picasso

class PhotoDeyFragment : Fragment(R.layout.fragment_photo_dey) {

    private var _binding: FragmentPhotoDeyBinding? = null
    private val binding get() = _binding!!

    private val app: App get() = requireActivity().application as App

    private val photoDayDtoRepo: PhotoDayDtoRepo by lazy {
        app.photoDayDtoRepo
    }

    private val viewModel: PhotoDeyViewModel by viewModels {
        PhotoDeyViewModel.Factory(
            photoDayDtoRepo
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentPhotoDeyBinding.bind(view)

        viewModel.inProgressLiveData.observe(viewLifecycleOwner) { inProgress ->
            binding.photoDeyImageView.isVisible = !inProgress
            binding.progressTaskBar.isVisible = inProgress
        }

        viewModel.photoDeyLiveData.observe(viewLifecycleOwner) {
            setPhotoDeyDto(it)
        }
    }

    private fun setPhotoDeyDto(photoDayDto: PhotoDayDto) {
        binding.dateTextView.text = photoDayDto.date
        binding.titleTextView.text = photoDayDto.title

        if (photoDayDto.url.isNotBlank()) {
            Picasso.get()
                .load(photoDayDto.url)
                .placeholder(R.drawable.uploading_images)
                .into(binding.photoDeyImageView)
            binding.photoDeyImageView.scaleType =
                ImageView.ScaleType.FIT_CENTER
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PhotoDeyFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}