package com.example.materialdesing.ui.recycler.change

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.materialdesing.databinding.FragmentRecyclerChangeItemEarthBinding
import com.example.materialdesing.databinding.FragmentRecyclerChangeItemHeaderBinding
import com.example.materialdesing.databinding.FragmentRecyclerChangeItemMarsBinding
import com.example.materialdesing.domain.entity.EntityTypeItems
import com.example.materialdesing.domain.entity.TYPE_EARTH
import com.example.materialdesing.domain.entity.TYPE_MARS
import com.example.materialdesing.domain.interactor.AddItemInteractor
import com.example.materialdesing.domain.interactor.RemoveItemInteractor

class RecyclerChangeAdapter(
    private var listData: List<EntityTypeItems>,
    val addItemInteractor: AddItemInteractor,
    val removeItemInteractor: RemoveItemInteractor
) : RecyclerView.Adapter<RecyclerChangeAdapter.BaseViewHolder>() {

    fun setListDataRemove(listEntityTypeItemsNew: List<EntityTypeItems>, position: Int) {
        listData = listEntityTypeItemsNew
        notifyItemRemoved(position) // анимированное действие с элементом
    }

    fun setListDataAdd(listEntityTypeItemsNew: List<EntityTypeItems>, position: Int) {
        listData = listEntityTypeItemsNew
        notifyItemInserted(position) // анимированное действие с элементом
    }

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            TYPE_EARTH -> {
                val binding =
                    FragmentRecyclerChangeItemEarthBinding.inflate(LayoutInflater.from(parent.context))
                EarthViewHolder(binding)
            }
            TYPE_MARS -> {
                val binding =
                    FragmentRecyclerChangeItemMarsBinding.inflate(LayoutInflater.from(parent.context))
                MarsViewHolder(binding)
            }
            else -> {
                val binding =
                    FragmentRecyclerChangeItemHeaderBinding.inflate(LayoutInflater.from(parent.context))
                HeaderViewHolder(binding)
            }
        }
    }

    /**
     * здесь связываем контейнер с контентом конкретног элемента
     * getItemViewType(position) - вызывается в любом месте адаптера (вызов по позиции)
     * То-есть узнаем позицию и в зависимости от результата поразному обработать
     */
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount() = listData.size

    /**
     * под каждую разметку (layour) создаем свой ViewHolder
     */
    class HeaderViewHolder(val binding: FragmentRecyclerChangeItemHeaderBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(entityTypeItems: EntityTypeItems) {
            binding.name.text = entityTypeItems.name
        }
    }

    inner class EarthViewHolder(val binding: FragmentRecyclerChangeItemEarthBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(entityTypeItems: EntityTypeItems) {
            binding.name.text = entityTypeItems.name
            binding.addItemImageView.setOnClickListener {
                addItemInteractor.add(layoutPosition)
            }
            binding.removeItemImageView.setOnClickListener {
                removeItemInteractor.remove(layoutPosition)
            }
        }
    }

    /**
     * inner -внутрений класс, тем самым получили доступ к аргументам адаптера
     * layoutPosition - то что видет пользователь
     * adapterPosition - это что внутри (чаще всего это одно и тоже, но быает когда пользователь нажал
     * а лайаут еще не успел отрисовать данные (это в редких случаях и при обработки тяжелых данных))
     * Если речь идет о том что пользователь чтото нажал но выбираем layoutPosition
     */
    inner class MarsViewHolder(val binding: FragmentRecyclerChangeItemMarsBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(entityTypeItems: EntityTypeItems) {
            binding.name.text = entityTypeItems.name
            binding.addItemImageView.setOnClickListener {
                addItemInteractor.add(layoutPosition)
            }
            binding.removeItemImageView.setOnClickListener {
                removeItemInteractor.remove(layoutPosition)
            }
        }
    }

    abstract class BaseViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        abstract fun bind(entityTypeItems: EntityTypeItems)
    }
}