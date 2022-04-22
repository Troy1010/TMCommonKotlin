package com.tminus1010.exampleclient.ui.main

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tminus1010.exampleclient.R
import com.tminus1010.exampleclient.databinding.FragMainBinding
import com.tminus1010.exampleclient.databinding.ItemButtonBinding
import com.tminus1010.tmcommonkotlin.customviews.vm_item.ButtonVMItem
import com.tminus1010.tmcommonkotlin.coroutines.extensions.observe
import com.tminus1010.tmcommonkotlin.customviews.extensions.bind
import com.tminus1010.tmcommonkotlin.view.extensions.nav
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFrag : Fragment(R.layout.frag_main) {
    private val viewModel by viewModels<MainVM>()
    lateinit var vb: FragMainBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb = FragMainBinding.bind(view)
        // # Events
        viewModel.navToImageToText.observe(lifecycleScope) { nav.navigate(R.id.imageToTextFrag) }
        // # State
        vb.recyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
        vb.recyclerview.bind(viewModel.buttons) {
            adapter = object : RecyclerView.Adapter<ViewHolder>() {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
                    ViewHolder(
                        ItemButtonBinding.inflate(layoutInflater, parent, false).apply {
                            btn.updateLayoutParams { height = (requireActivity().window.decorView.height * 0.2).toInt() }
                        }
                    )

                override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(it[position])
                override fun getItemCount() = it.size
            }
        }
    }

    class ViewHolder(val vb: ItemButtonBinding) : RecyclerView.ViewHolder(vb.root) {
        fun bind(buttonVMItem: ButtonVMItem) {
            buttonVMItem.bind(vb.btn)
        }
    }
}