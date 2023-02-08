package com.example.materialdesing.ui.recycler.change

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.materialdesing.R
import com.example.materialdesing.databinding.FragmentRecyclerChangeBinding
import com.example.materialdesing.domain.entity.EntityTypeItems
import com.example.materialdesing.domain.entity.TYPE_EARTH
import com.example.materialdesing.domain.entity.TYPE_HEADER
import com.example.materialdesing.domain.entity.TYPE_MARS
import com.example.materialdesing.domain.interactor.AddItemInteractor
import com.example.materialdesing.domain.interactor.RemoveItemInteractor

class RecyclerChangeFragment : Fragment(R.layout.fragment_recycler_change) {

    private var _binding: FragmentRecyclerChangeBinding? = null
    private val binding get() = _binding!!
    private var idCounter: Long = 0

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var adapter: RecyclerChangeAdapter

    private val data: MutableList<EntityTypeItems> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentRecyclerChangeBinding.bind(view)

        adapter = RecyclerChangeAdapter(data, addItemInteractor, removeItemInteractor)

        // созданный
        binding.recyclerView.adapter = adapter
    }

    init {
        data.add(EntityTypeItems(0, "Заголовок", type = TYPE_HEADER))
        data.add(EntityTypeItems(1, "Земля", type = TYPE_EARTH))
        data.add(EntityTypeItems(2, "Земля", type = TYPE_EARTH))
        data.add(EntityTypeItems(3, "Марс", type = TYPE_MARS))
        data.add(EntityTypeItems(4, "Земля", type = TYPE_EARTH))
        data.add(EntityTypeItems(5, "Земля", type = TYPE_EARTH))
        data.add(EntityTypeItems(6, "Земля", type = TYPE_EARTH))
        data.add(EntityTypeItems(7, "Марс", type = TYPE_MARS))
        idCounter = 7
    }

    private val addItemInteractor = AddItemInteractor {
        when (it) {
            TYPE_EARTH -> {
                data.add(it, EntityTypeItems(idCounter++, "Земля(New)" + it, type = TYPE_EARTH))

            }
            TYPE_MARS -> {
                data.add(it, EntityTypeItems(idCounter++, "Марс(New)" + it, type = TYPE_MARS))
            }
        }
        adapter.setListDataAdd(data, it)
    }

    private val removeItemInteractor = RemoveItemInteractor {
        data.removeAt(it)
        adapter.setListDataRemove(data, it)
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