package com.tminus1010.tmcommonkotlin.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tminus1010.tmcommonkotlin.androidx.ExposedLifecycleOwner
import com.tminus1010.tmcommonkotlin.androidx.R
import com.tminus1010.tmcommonkotlin.androidx.databinding.ItemButtonBinding
import com.tminus1010.tmcommonkotlin.androidx.extensions.lifecycleOwner
import com.tminus1010.tmcommonkotlin.customviews.vm_item.ButtonVMItem

class ButtonsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.editTextStyle,
) : RecyclerView(context, attrs, defStyleAttr) {
    var buttons = emptyList<ButtonVMItem>()
        set(value) {
            field = value; adapter?.notifyDataSetChanged()
        }

    init {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        addItemDecoration(MarginDecoration(7))
        adapter = object : RecyclerView.Adapter<ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
                ViewHolder(ItemButtonBinding.inflate(LayoutInflater.from(context), parent, false))

            override fun getItemCount() = buttons.size
            override fun onBindViewHolder(holder: ViewHolder, position: Int) = Unit // do nothing
            override fun onViewAttachedToWindow(holder: ViewHolder) {
                super.onViewAttachedToWindow(holder)
                holder.itemView.lifecycleOwner = ExposedLifecycleOwner().apply { emitResume() }
                holder.bind(buttons[holder.bindingAdapterPosition])
            }

            override fun onViewDetachedFromWindow(holder: ViewHolder) {
                super.onViewDetachedFromWindow(holder)
                (holder.itemView.lifecycleOwner as ExposedLifecycleOwner).emitDestroy()
            }
        }
    }

    class ViewHolder(val vb: ItemButtonBinding) : RecyclerView.ViewHolder(vb.root) {
        fun bind(buttonVMItem: ButtonVMItem) {
            buttonVMItem.bind(vb.btn)
        }
    }
}