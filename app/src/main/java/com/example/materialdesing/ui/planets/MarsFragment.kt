package com.example.materialdesing.ui.planets

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.viewModels
import com.example.materialdesing.App
import com.example.materialdesing.R
import com.example.materialdesing.databinding.FragmentMarsBinding
import com.example.materialdesing.domain.repo.EarthRepo
import com.example.materialdesing.domain.repo.MarsRepo
import com.example.materialdesing.ui.ViewBindingFragment
import com.example.nasaapp.model.data.MarsServerResponseData
import com.squareup.picasso.Picasso

class MarsFragment : ViewBindingFragment<FragmentMarsBinding>(
    FragmentMarsBinding::inflate
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.marsLiveData.observe(viewLifecycleOwner) {
            setPhotoDto(
                it.last()
            )
        }
    }

    private fun setPhotoDto(marsPhoto: MarsServerResponseData) {
        val image = marsPhoto.imgSrc

        val url =
            "https://mars.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/03715/opgs/edr/fcam/$image.jpg"
        Picasso.get()
            .load(image)
            .fit() // картинка будет размещена по выделенному размеру для нее.
            .placeholder(R.drawable.uploading_images)
            .into(binding.fotoMarsImageView)
        binding.fotoMarsImageView.scaleType =
            ImageView.ScaleType.FIT_CENTER
    }
}
