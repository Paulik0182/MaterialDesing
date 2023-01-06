package com.example.materialdesing.ui.nasa

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.materialdesing.App
import com.example.materialdesing.R
import com.example.materialdesing.databinding.FragmentPhotoDescriptionBinding
import com.example.materialdesing.domain.entity.PhotoDto
import com.example.materialdesing.domain.repo.PhotoRepo
import com.example.materialdesing.utils.toastMake
import com.squareup.picasso.Picasso

class PhotoDeyFragment : Fragment(R.layout.fragment_photo_description) {

    private var _binding: FragmentPhotoDescriptionBinding? = null
    private val binding get() = _binding!!

    private val app: App get() = requireActivity().application as App

    private val photoRepo: PhotoRepo by lazy {
        app.photoRepo
    }

    private val viewModel: PhotoDeyViewModel by viewModels {
        PhotoDeyViewModel.Factory(
            photoRepo
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentPhotoDescriptionBinding.bind(view)

        viewModel.inProgressLiveData.observe(viewLifecycleOwner) { inProgress ->
            binding.photoDeyImageView.isVisible = !inProgress
            binding.progressTaskBar.isVisible = inProgress
        }

        binding.todayChip.performClick()
        setPhotoDey()

        binding.todayChip.setOnClickListener {
            setPhotoDey()
            context?.toastMake("Сегодня")
        }

        binding.yesterdayChip.setOnClickListener {
            viewModel.yesterdayLiveData.observe(viewLifecycleOwner) {
                setPhotoDto(it)
            }
            context?.toastMake("Вчера")
        }

        binding.twoDaysAgoChip.setOnClickListener {
            viewModel.twoDaysAgoLiveData.observe(viewLifecycleOwner) {
                setPhotoDto(it)
            }
            context?.toastMake("Позавчера")
        }

        binding.fab.setOnClickListener {
            setPhotoDey()
            binding.todayChip.performClick()
        }
    }

    private fun setPhotoDey() {
        viewModel.photoDeyLiveData.observe(viewLifecycleOwner) {
            setPhotoDto(it)
        }
    }

    private fun setPhotoDto(photoDto: PhotoDto) {
        binding.dateTextView.text = photoDto.date
        binding.titleTextView.text = photoDto.title
        binding.explanationTextView.text = photoDto.explanation

        if (photoDto.url.isNotBlank()) {
            Picasso.get()
                .load(photoDto.url)
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