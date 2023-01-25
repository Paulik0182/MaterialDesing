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

                    viewModel.earthLastWeekLiveData.observe(viewLifecycleOwner) {
                        setPhotoDto(
                            it.last()
                        )
                        Toast.makeText(requireContext(), "Привет", Toast.LENGTH_SHORT).show()
                    }
                }
//                YESTERDAY_EARTH -> {
//                    Toast.makeText(requireContext(), "1", Toast.LENGTH_SHORT).show()
//                    binding.yesterdayPaginationImageView.setImageResource(
//                        R.drawable.swipe_indicator_active
//                    )
////                    setPhotoDto(R.drawable.ic_mars)
//                    binding.fotoEarthImageView.setImageResource(R.drawable.ic_mars)
//                }
//                TWO_DATS_AGO_EARTH -> {
//                    binding.dayBeforeYesterdayPaginationImageView.setImageResource(
//                        R.drawable.swipe_indicator_active
//                    )
////                    setPhotoDto(R.drawable.ic_system)
//                    binding.fotoEarthImageView.setImageResource(R.drawable.ic_system)
//                }
                else -> true
            }
        }
    }

    private fun setPhotoDto(earthDtoItem: EarthDtoItem) {
        binding.dateTextView.text = earthDtoItem.date
        val year = earthDtoItem.date?.slice(0..3)
        val month = earthDtoItem.date?.slice(5..6)
        val day = earthDtoItem.date?.slice(8..9)
        val image = earthDtoItem.image

        val url = "https://epic.gsfc.nasa.gov/archive/natural/$year/$month/$day/jpg/$image.jpg"

        Picasso.get()
            .load(url)
            .placeholder(R.drawable.uploading_images)
            .into(binding.fotoEarthImageView)
        binding.fotoEarthImageView.scaleType =
            ImageView.ScaleType.FIT_CENTER
    }

    companion object {
        private const val TODAY_EARTH = 0
        private const val YESTERDAY_EARTH = 1
        private const val TWO_DATS_AGO_EARTH = 2

        @JvmStatic
        fun newInstance() = FotoEarthFragment()
    }
}
