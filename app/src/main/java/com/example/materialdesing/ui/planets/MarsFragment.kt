package com.example.materialdesing.ui.planets

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.materialdesing.App
import com.example.materialdesing.R
import com.example.materialdesing.databinding.FragmentMarsBinding
import com.example.materialdesing.domain.repo.EarthRepo
import com.example.materialdesing.domain.repo.MarsRepo
import com.squareup.picasso.Picasso
import ru.geekbrains.nasaapi.repository.dto.MarsPhotosServerResponseData

class MarsFragment : Fragment(R.layout.fragment_mars) {

    private var _biding: FragmentMarsBinding? = null
    private val binding get() = _biding!!

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

        _biding = FragmentMarsBinding.bind(view)

        viewModel.marsLiveData.observe(viewLifecycleOwner) {
            setPhotoDto(it)
        }
    }

    private fun setPhotoDto(marsPhoto: MarsPhotosServerResponseData) {

        Picasso.get()
            .load(marsPhoto.photos.last().imgSrc)
            .fit() // картинка будет размещена по выделенному размеру для нее.
            .placeholder(R.drawable.uploading_images)
            .into(binding.fotoMarsImageView)
        binding.fotoMarsImageView.scaleType =
            ImageView.ScaleType.FIT_CENTER
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _biding = null
    }
}
