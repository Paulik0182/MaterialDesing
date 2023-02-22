package com.example.materialdesing.ui.showcase

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

private const val NUMBER_ITEMS_ON_SCREEN = 32

class ShowcaseAdapter(
    private var openApp: () -> Unit
) : RecyclerView.Adapter<ShowcaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowcaseViewHolder {
        return ShowcaseViewHolder(
            parent,
            openApp
        )
    }

    override fun getItemCount(): Int = NUMBER_ITEMS_ON_SCREEN

    override fun onBindViewHolder(holder: ShowcaseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun getItem(position: Int): Int = position
}