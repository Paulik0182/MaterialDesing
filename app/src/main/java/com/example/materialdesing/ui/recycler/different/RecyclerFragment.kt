package com.example.materialdesing.ui.recycler.different

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.materialdesing.R
import com.example.materialdesing.databinding.FragmentRecyclerBinding
import com.example.materialdesing.domain.entity.EntityTypeItems
import com.example.materialdesing.domain.entity.TYPE_EARTH
import com.example.materialdesing.domain.entity.TYPE_HEADER
import com.example.materialdesing.domain.entity.TYPE_MARS

class RecyclerFragment : Fragment(R.layout.fragment_recycler) {

    private var _binding: FragmentRecyclerBinding? = null
    private val binding get() = _binding!!

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentRecyclerBinding.bind(view)

        val data = arrayListOf(
            EntityTypeItems(0, "Заголовок", type = TYPE_HEADER),
            EntityTypeItems(1, "Земля", type = TYPE_EARTH),
            EntityTypeItems(2, "Земля", type = TYPE_EARTH),
            EntityTypeItems(3, "Марс", type = TYPE_MARS),
            EntityTypeItems(4, "Земля", type = TYPE_EARTH),
            EntityTypeItems(5, "Земля", type = TYPE_EARTH),
            EntityTypeItems(6, "Земля", type = TYPE_EARTH),
            EntityTypeItems(7, "Марс", type = TYPE_MARS)
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