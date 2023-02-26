package com.example.materialdesing.ui.showcase

import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Explode
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.example.materialdesing.R
import com.example.materialdesing.databinding.ActivityAnimationBinding

private const val TIMEOUT = 1_800L
class ShowcaseViewHolder(
    parent: ViewGroup,
    private var openApp: () -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.activity_animation_explode_recycle_view_item, parent, false)
) {

    private val binding: ActivityAnimationBinding =
        ActivityAnimationBinding.bind(itemView)

    fun bind(item: Int) {
        itemView.setOnClickListener {

            /*    val rect = Rect(
                    it.x.toInt(),
                    it.y.toInt(),
                    it.x.toInt() + it.width.toInt(),
                    it.x.toInt().toInt() + it.height
                )*/
            val rect = Rect()
            it.getGlobalVisibleRect(rect)
            val explode = Explode()
            explode.duration = 2000L
            explode.epicenterCallback = object : Transition.EpicenterCallback() {
                override fun onGetEpicenter(transition: Transition): Rect {
                    return rect
                }
            }
            TransitionManager.beginDelayedTransition(binding.recyclerView, explode)
            binding.recyclerView.adapter = null

            Handler(Looper.getMainLooper()).postDelayed({
                openApp()
            }, TIMEOUT)
        }
    }
}
