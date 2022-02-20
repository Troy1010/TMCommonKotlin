package com.tminus1010.tmcommonkotlin.misc

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class GenViewHolder<T : ViewBinding>(val vb: T) : RecyclerView.ViewHolder(vb.root)