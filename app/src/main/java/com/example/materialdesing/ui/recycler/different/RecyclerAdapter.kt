package com.example.materialdesing.ui.recycler.different

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.materialdesing.databinding.FragmentRecyclerItemEarthBinding
import com.example.materialdesing.databinding.FragmentRecyclerItemHeaderBinding
import com.example.materialdesing.databinding.FragmentRecyclerItemMarsBinding
import com.example.materialdesing.ui.recycler.Data
import com.example.materialdesing.ui.recycler.TYPE_EARTH
import com.example.materialdesing.ui.recycler.TYPE_MARS

class RecyclerAdapter(
    private val listData: List<Data>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    // определяем на какой позиции кто должен находится (у нас пример из трех item)
    override fun getItemViewType(position: Int): Int {
        return listData[position].type // установили значение по позиции type
    }

    /**
     *  Пример нескольких items (разные элементы). Раздуваем разные layout.
     *  Inflater - строит по шаблону, по макету объект (раздувает - создаем объект)
     *  ViewHolder - это абстрактный класс (нельзя создать объект), поэтому создается под каждую
     *  разметку свой ViewHolder
     */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_EARTH -> {
                val binding =
                    FragmentRecyclerItemEarthBinding.inflate(LayoutInflater.from(parent.context))
                EarthViewHolder(binding)
            }
            TYPE_MARS -> {
                val binding =
                    FragmentRecyclerItemMarsBinding.inflate(LayoutInflater.from(parent.context))
                MarsViewHolder(binding)
            }
            else -> {
                val binding =
                    FragmentRecyclerItemHeaderBinding.inflate(LayoutInflater.from(parent.context))
                HeaderViewHolder(binding)
            }
        }
    }

    /**
     * здесь связываем контейнер с контентом конкретног элемента
     * getItemViewType(position) - вызывается в любом месте адаптера (вызов по позиции)
     * То-есть узнаем позицию и в зависимости от результата поразному обработать
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_EARTH -> {
                (holder as EarthViewHolder).bind(listData[position])
            }
            TYPE_MARS -> {
                (holder as MarsViewHolder).bind(listData[position])
            }
            else -> {
                (holder as HeaderViewHolder).bind(listData[position])
            }
        }
    }

    override fun getItemCount() = listData.size

    /**
     * под каждую разметку (layour) создаем свой ViewHolder
     */
    class HeaderViewHolder(val binding: FragmentRecyclerItemHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data) {
            binding.name.text = data.name
        }
    }

    class EarthViewHolder(val binding: FragmentRecyclerItemEarthBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data) {
            binding.name.text = data.name
        }
    }

    class MarsViewHolder(val binding: FragmentRecyclerItemMarsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data) {
            binding.name.text = data.name
        }
    }
}