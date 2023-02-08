package com.example.materialdesing.ui.recycler

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.materialdesing.R
import com.example.materialdesing.databinding.FragmentRootRecylerBinding

class RootRecyclerFragment : Fragment(R.layout.fragment_root_recyler) {

    private var _binding: FragmentRootRecylerBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentRootRecylerBinding.bind(view)

        binding.typesItemsButton.setOnClickListener {
            getController().openTypesItems()
        }

        binding.recyclerChangeButton.setOnClickListener {
            getController().openRecyclerChange()
        }
    }

    interface Controller {
        fun openTypesItems()
        fun openRecyclerChange()
    }

    private fun getController(): Controller = activity as Controller

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getController()
    }

    companion object {
        @JvmStatic
        fun newInstance() = RootRecyclerFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}