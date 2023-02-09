package com.example.materialdesing.ui.recycler.change

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.ItemTouchHelper
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

    private val data: MutableList<Pair<EntityTypeItems, Boolean>> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentRecyclerChangeBinding.bind(view)

        adapter = RecyclerChangeAdapter(data, addItemInteractor, removeItemInteractor)

        // созданный
        binding.recyclerView.adapter = adapter

        // запускаем ItemTouchHelper
        ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(binding.recyclerView)

        // Пример связывания двух чисел
        val lat = 10
        val lon = 10
        val location = lat to lon // связываем два числа (это абстракция). Вариант 1
        location.first // получаем широту
        location.second // получаем долготу
        val location2 = Pair(lat, lon) // связываем два числа (это абстракция). Вариант 2
        // Используем связывание для хранения состояния
    }

    init {
        data.add(Pair(EntityTypeItems(0, "Заголовок", type = TYPE_HEADER), false))
        data.add(Pair(EntityTypeItems(1, "Земля", type = TYPE_EARTH), false))
        data.add(Pair(EntityTypeItems(2, "Земля", type = TYPE_EARTH), false))
        data.add(Pair(EntityTypeItems(3, "Марс", type = TYPE_MARS), false))
        data.add(Pair(EntityTypeItems(4, "Земля", type = TYPE_EARTH), false))
        data.add(Pair(EntityTypeItems(5, "Земля", type = TYPE_EARTH), false))
        data.add(Pair(EntityTypeItems(6, "Земля", type = TYPE_EARTH), false))
        data.add(Pair(EntityTypeItems(7, "Марс", type = TYPE_MARS), false))
        idCounter = 7
    }

    private val addItemInteractor = AddItemInteractor {
        when (it) {
            TYPE_EARTH -> {
                data.add(
                    it,
                    Pair(
                        EntityTypeItems(idCounter++, "Земля(New)" + it, type = TYPE_EARTH),
                        false
                    )
                )
            }
            TYPE_MARS -> {
                data.add(
                    it, Pair(
                        EntityTypeItems(idCounter++, "Марс(New)" + it, type = TYPE_MARS), false
                    )
                )
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