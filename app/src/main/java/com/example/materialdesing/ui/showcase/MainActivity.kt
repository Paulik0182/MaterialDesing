package com.example.materialdesing.ui.showcase

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Explode
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.example.materialdesing.R
import com.example.materialdesing.databinding.ActivityAnimationBinding
import com.example.materialdesing.ui.RootActivity

private const val NUMBER_ITEMS_ON_SCREEN = 32
private const val TIMEOUT = 1_800L
private const val TIMEOUT_ACTION_BREAK = 2_000L

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnimationBinding

    private var isFlag = false

//    private lateinit var adapter: ShowcaseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
//        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isFlag = !isFlag

        val intent = Intent(this, RootActivity::class.java)

        binding.recyclerView.adapter = Adapter { startActivity(intent) }

//        initViews()
    }

//    private fun initViews(view: View) {
//        binding.recyclerView.layoutManager = LinearLayoutManager(this)
//        adapter = ShowcaseAdapter()
//        binding.recyclerView.adapter = adapter
//    }

    inner class Adapter(
        private var openApp: () -> Unit
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return MyViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.activity_animation_explode_recycle_view_item,
                    parent,
                    false
                ) as View
            )
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            holder.itemView.setOnClickListener {

                /*    val rect = Rect(
                        it.x.toInt(),
                        it.y.toInt(),
                        it.x.toInt() + it.width.toInt(),
                        it.x.toInt().toInt() + it.height
                    )*/
                val rect = Rect()
                it.getGlobalVisibleRect(rect)
                val explode = Explode()
                explode.duration = TIMEOUT_ACTION_BREAK
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

        override fun getItemCount(): Int = NUMBER_ITEMS_ON_SCREEN

        inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
    }
}