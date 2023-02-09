package com.example.materialdesing.ui.recycler.change

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.gb.m_2090_3.view.recycler.ItemTouchHelperAdapter

class ItemTouchHelperCallback(private val callback: ItemTouchHelperAdapter) :
    ItemTouchHelper.Callback() {
    // метод описывает в какие стороны можно двигать или свайпать
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val swipe = ItemTouchHelper.END or ItemTouchHelper.START // можно сдвигать в право и в лево
        val drag = ItemTouchHelper.UP or ItemTouchHelper.DOWN // можно тащить в верх и вниз
        return makeMovementFlags(
            drag,
            swipe
        ) // возвращаем связанные значения (порядок возврата соблюдать)
    }

    // метод описывает что нужно сделать
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        // перемещение произошло из viewHolder - в target
        // callback - это часть адаптера
        callback.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        callback.onItemDismiss(viewHolder.adapterPosition)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        viewHolder?.let {
            (it as RecyclerChangeAdapter.BaseViewHolder).onItemSelect()
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        (viewHolder as RecyclerChangeAdapter.BaseViewHolder).onItemClear()
    }
}