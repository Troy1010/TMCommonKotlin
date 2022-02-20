package com.tminus1010.tmcommonkotlin.misc.tmTableView

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.tminus1010.tmcommonkotlin.misc.GenViewHolder

class InnerRVAdapter(
    private val recipeGrid: RecipeGrid3,
    private val j: Int,
) : LifecycleRVAdapter2<GenViewHolder<ViewBinding>>() {
    override fun onCreateViewHolder(parent: ViewGroup, i: Int): GenViewHolder<ViewBinding> =
        GenViewHolder(recipeGrid.createResizedView(i, j))

    override fun getItemViewType(position: Int) = position

    override fun getItemCount() = recipeGrid[0].size

    override fun onLifecycleAttached(holder: GenViewHolder<ViewBinding>) {
        recipeGrid[j][holder.adapterPosition].bind(holder.vb)
    }
}