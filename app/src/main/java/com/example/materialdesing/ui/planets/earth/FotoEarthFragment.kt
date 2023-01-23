package com.example.materialdesing.ui.planets.earth

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.materialdesing.App
import com.example.materialdesing.R
import com.example.materialdesing.databinding.FragmentFotoEarthBinding
import com.example.materialdesing.domain.entity.earth.EarthDtoItem
import com.example.materialdesing.domain.repo.EarthRepo
import com.example.materialdesing.domain.repo.MarsRepo
import com.example.materialdesing.ui.planets.PlanetsViewModel
import com.squareup.picasso.Picasso

class FotoEarthFragment : Fragment(R.layout.fragment_foto_earth) {

    private var _binding: FragmentFotoEarthBinding? = null
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

    private lateinit var adapter: ViewPagerEarthAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFotoEarthBinding.bind(view)

        adapter = ViewPagerEarthAdapter(this)
        setTabs()
    }

    private fun setTabs() {

        adapter.fragments.forEachIndexed { index, _ ->
            when (index) {
                TODAY_EARTH -> {
                    Toast.makeText(requireContext(), "0", Toast.LENGTH_SHORT).show()
                    onDefaultPagination()
                    binding.todayPaginationImageView.setImageResource(
                        R.drawable.swipe_indicator_active
                    )
//                    setPhotoDto(R.drawable.ic_earth)
                    binding.fotoEarthImageView.setImageResource(R.drawable.ic_earth)
                }
                YESTERDAY_EARTH -> {
                    Toast.makeText(requireContext(), "1", Toast.LENGTH_SHORT).show()
                    onDefaultPagination()
                    binding.yesterdayPaginationImageView.setImageResource(
                        R.drawable.swipe_indicator_active
                    )
//                    setPhotoDto(R.drawable.ic_mars)
                    binding.fotoEarthImageView.setImageResource(R.drawable.ic_mars)
                }
                TWO_DATS_AGO_EARTH -> {
                    onDefaultPagination()
                    binding.dayBeforeYesterdayPaginationImageView.setImageResource(
                        R.drawable.swipe_indicator_active
                    )
//                    setPhotoDto(R.drawable.ic_system)
                    binding.fotoEarthImageView.setImageResource(R.drawable.ic_system)
                }
                else -> true
            }
        }
    }

    private fun setPhotoDto(photo: Int) {
        if (photo != null) {
            Picasso.get()
                .load(photo)
                .placeholder(R.drawable.uploading_images)
                .into(binding.fotoEarthImageView)
            binding.fotoEarthImageView.scaleType =
                ImageView.ScaleType.FIT_CENTER
        }
    }


    private fun setPhotoDto(earthDtoItem: EarthDtoItem) {
        binding.fotoEarthImageView.setImageResource(R.drawable.ic_earth)
        earthDtoItem.image
        if (earthDtoItem.image.isNotBlank()) {
            Picasso.get()
                .load(earthDtoItem.image)
                .fit() // картинка будет размещена по выделенному размеру для нее.
                .placeholder(R.drawable.uploading_images)
                .into(binding.fotoEarthImageView)
            binding.fotoEarthImageView.scaleType =
                ImageView.ScaleType.FIT_CENTER
        }
    }

    private fun onDefaultPagination() {
        binding.todayPaginationImageView.setImageResource(R.drawable.swipe_indicator_passive)
        binding.yesterdayPaginationImageView.setImageResource(R.drawable.swipe_indicator_passive)
        binding.dayBeforeYesterdayPaginationImageView.setImageResource(
            R.drawable.swipe_indicator_passive
        )
    }

    companion object {
        private const val TODAY_EARTH = 0
        private const val YESTERDAY_EARTH = 1
        private const val TWO_DATS_AGO_EARTH = 2
    }
}
