package com.example.materialdesing.ui.recycler.different

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.materialdesing.R
import com.example.materialdesing.databinding.FragmentRecyclerBinding
import com.example.materialdesing.ui.recycler.Data
import com.example.materialdesing.ui.recycler.TYPE_EARTH
import com.example.materialdesing.ui.recycler.TYPE_HEADER
import com.example.materialdesing.ui.recycler.TYPE_MARS

class RecyclerFragment : Fragment(R.layout.fragment_recycler) {

    private var _binding: FragmentRecyclerBinding? = null
    private val binding get() = _binding!!

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentRecyclerBinding.bind(view)

        val data = arrayListOf(
            Data("Заголовок", type = TYPE_HEADER),
            Data("Земля", type = TYPE_EARTH),
            Data("Земля", type = TYPE_EARTH),
            Data("Марс", type = TYPE_MARS),
            Data("Земля", type = TYPE_EARTH),
            Data("Земля", type = TYPE_EARTH),
            Data("Земля", type = TYPE_EARTH),
            Data("Марс", type = TYPE_MARS)
        )

        // созданный
        binding.recyclerView.adapter = RecyclerAdapter(data)
    }

    companion object {
        @JvmStatic
        fun newInstance() = RecyclerFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}