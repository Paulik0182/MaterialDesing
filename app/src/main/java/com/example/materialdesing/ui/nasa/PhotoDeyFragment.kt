package com.example.materialdesing.ui.nasa

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.transition.*
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

    private var flagVisible = true
    private var flagApproximationImage = true

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
//            setPhotoDey() // возврат на сегодняшний день

            flagVisible = !flagVisible

            // Все должно быть из androidX. Проверять если будет ругатся
            val myAutoTransition =
                TransitionSet()// состоит из нескольких параметров, поэтому TransitionSet
//            myAutoTransition.ordering = TransitionSet.ORDERING_SEQUENTIAL
            myAutoTransition.ordering = TransitionSet.ORDERING_TOGETHER
//            val fade = Hold()
            val fade = Slide(Gravity.BOTTOM)
            fade.duration = 1_000L
            val changeBounds = ChangeBounds()
            changeBounds.duration = 1_000L
            myAutoTransition.addTransition(changeBounds)
            myAutoTransition.addTransition(fade)
            TransitionManager.beginDelayedTransition(binding.transitionsContainer, myAutoTransition)

            binding.inputLayoutChipGroup.visibility = if (flagVisible) View.GONE else View.VISIBLE
//            binding.todayChip.performClick() // устанавливаем отметку нажатия
        }
        binding.inputLayoutChipGroup.visibility = View.GONE

        setApproximationImage()
    }

    // Приближение (увеличение) картинки по нажатию на нее
    private fun setApproximationImage() {
        binding.photoDeyImageView.setOnClickListener {
            flagApproximationImage = !flagApproximationImage

            // плавное сокрытие элементов
            val myAutoTransition =
                TransitionSet()// состоит из нескольких параметров, поэтому TransitionSet
//            myAutoTransition.ordering = TransitionSet.ORDERING_SEQUENTIAL
            myAutoTransition.ordering = TransitionSet.ORDERING_TOGETHER
//            val fade = Hold()
            val fade = Slide(Gravity.BOTTOM)
            fade.duration = 2_000L
            val changeBounds1 = ChangeBounds()
            changeBounds1.duration = 2_000L
            myAutoTransition.addTransition(changeBounds1)
            myAutoTransition.addTransition(fade)
            TransitionManager.beginDelayedTransition(binding.transitionsContainer, myAutoTransition)
            binding.coordinatorLayout.visibility =
                if (flagApproximationImage) View.GONE else View.VISIBLE

            // Приближение (увеличение) картинки
            val params = it.layoutParams as LinearLayout.LayoutParams
            val transitionSet = TransitionSet()
            val changeImageTransform = ChangeImageTransform()
            val changeBounds = ChangeBounds()
            changeBounds.duration = 2000L
            changeImageTransform.duration = 2000L

            transitionSet.ordering = TransitionSet.ORDERING_TOGETHER
            transitionSet.addTransition(changeBounds)// важен порядок, обязетельно changeImageTransform после changeBounds
            transitionSet.addTransition(changeImageTransform)

            TransitionManager.beginDelayedTransition(binding.root, transitionSet)
            if (flagApproximationImage) {
                params.height = LinearLayout.LayoutParams.MATCH_PARENT
                binding.photoDeyImageView.scaleType = ImageView.ScaleType.CENTER_CROP
            } else {

                params.height = LinearLayout.LayoutParams.WRAP_CONTENT
                binding.photoDeyImageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
            }
            binding.photoDeyImageView.layoutParams = params
        }
    }


    private fun onClickIcon() {
        binding.inputLayout.setEndIconOnClickListener {
            Toast.makeText(requireContext(), "Wiki", Toast.LENGTH_SHORT).show()
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse(
                        "https://en.wikipedia.org/wiki/" +
                                binding.inputEditText.text.toString()
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