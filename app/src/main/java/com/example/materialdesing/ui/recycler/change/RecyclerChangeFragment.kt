package com.example.materialdesing.ui.recycler.change

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.materialdesing.R
import com.example.materialdesing.databinding.FragmentRecyclerChangeBinding
import com.example.materialdesing.domain.interactor.RemoveItemInteractor
import com.example.materialdesing.ui.recycler.Data
import com.example.materialdesing.ui.recycler.TYPE_EARTH
import com.example.materialdesing.ui.recycler.TYPE_HEADER
import com.example.materialdesing.ui.recycler.TYPE_MARS

class RecyclerChangeFragment : Fragment(R.layout.fragment_recycler_change) {

    private var _binding: FragmentRecyclerChangeBinding? = null
    private val binding get() = _binding!!

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var adapter: RecyclerChangeAdapter

    private val data = arrayListOf(
        Data("Заголовок", type = TYPE_HEADER),
        Data("Земля", type = TYPE_EARTH),
        Data("Земля", type = TYPE_EARTH),
        Data("Марс", type = TYPE_MARS),
        Data("Земля", type = TYPE_EARTH),
        Data("Земля", type = TYPE_EARTH),
        Data("Земля", type = TYPE_EARTH),
        Data("Марс", type = TYPE_MARS)
    )

//    private val addItemInteractor = AddItemInteractor {
//        when (it) {
//            TYPE_EARTH -> {
//                data.add(it, Data("Земля(New)"+it, type= TYPE_EARTH))
//
//            }
//            TYPE_MARS -> {
//                data.add(it, Data("Марс(New)"+it, type= TYPE_MARS))
//            }
//        }
//        adapter.setListDataAdd(data,+it)
//    }

    private val removeItemInteractor = RemoveItemInteractor {
        data.removeAt(it)
        adapter.setListDataRemove(data, it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentRecyclerChangeBinding.bind(view)

        adapter = RecyclerChangeAdapter(data, removeItemInteractor)

        // созданный
        binding.recyclerView.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = RecyclerChangeFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}