package com.example.materialdesing.ui.showcase

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ShowcaseAdapter : RecyclerView.Adapter<ShowcaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowcaseViewHolder {
        return ShowcaseViewHolder(parent)
    }

    override fun getItemCount(): Int = 32

    override fun onBindViewHolder(holder: ShowcaseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun getItem(position: Int): Int = position
}