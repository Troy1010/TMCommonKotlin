package com.example.tmcommonkotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TMRecyclerViewAdapter(
    var binder: Callbacks,
    var context: Context,
    val item_layout: Int
): RecyclerView.Adapter<TMRecyclerViewAdapter.ViewHolder>() {
    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TMRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return binder.getRecyclerDataSize()
    }

    override fun onBindViewHolder(holder: TMRecyclerViewAdapter.ViewHolder, position: Int) {
        binder.bindRecyclerItemView(holder.itemView, position)
    }

    interface Callbacks
    {
        fun bindRecyclerItemView(view: View, i: Int)
        fun getRecyclerDataSize() : Int
    }

}