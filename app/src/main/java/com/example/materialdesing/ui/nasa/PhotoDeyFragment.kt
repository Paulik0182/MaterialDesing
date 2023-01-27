package com.example.materialdesing.ui.nasa

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.materialdesing.App
import com.example.materialdesing.R
import com.example.materialdesing.databinding.FragmentPhotoDescriptionCoordinatorBinding
import com.example.materialdesing.domain.entity.PhotoDto
import com.example.materialdesing.domain.repo.PhotoRepo
import com.example.materialdesing.utils.toastMake
import com.squareup.picasso.Picasso

class PhotoDeyFragment : Fragment(R.layout.fragment_photo_description_coordinator) {

    private var _binding: FragmentPhotoDescriptionCoordinatorBinding? = null
    private val binding get() = _binding!!

    var flag = true
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

        _binding = FragmentPhotoDescriptionCoordinatorBinding.bind(view)

        onClickIcon()

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
            flag = !flag
            binding.inputLayoutChipGroup.visibility = if (flag) View.GONE else View.VISIBLE
        }

        binding.inputLayoutChipGroup.visibility = View.GONE
    }

    private fun onClickIcon() {
        binding.inputLayout.setEndIconOnClickListener {
            Toast.makeText(requireContext(), "Wiki", Toast.LENGTH_SHORT).show()
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse(
                        "https://en.wikipedia.org/wiki/" +
                                "${
                                    binding.inputEditText.text.toString()
                                }"
                    )
            })
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