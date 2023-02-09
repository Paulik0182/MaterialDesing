package com.gb.m_2090_3.view.recycler

interface ItemTouchHelperAdapter {
    fun onItemMove(
        fromPosition: Int,
        toPosition: Int
    ) // прозошло перемещение с одной позии на другую

    fun onItemDismiss(position: Int) // элемент уничтожен на такойто позиции
}

interface ItemTouchHelperViewHolder {
    fun onItemSelect() // выбран элемент
    fun onItemClear() // отпустили элемент
}