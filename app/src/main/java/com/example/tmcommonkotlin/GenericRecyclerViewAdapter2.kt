package com.example.tmcommonkotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

open class GenericRecyclerViewAdapter2(
    var binder: Callbacks,
    var context: Context,
    val item_layout: Int
): RecyclerView.Adapter<GenericRecyclerViewAdapter2.ViewHolder>() {
    class ViewHolder (view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return binder.getRecyclerDataSize()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        binder.bindRecyclerItem(holder, holder.itemView)
    }

    interface Callbacks
    {
        fun bindRecyclerItem(holder: ViewHolder, view: View)
        fun getRecyclerDataSize() : Int
    }

}