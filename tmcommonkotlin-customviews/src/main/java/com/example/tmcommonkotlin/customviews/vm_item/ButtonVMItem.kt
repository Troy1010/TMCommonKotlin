package com.tminus1010.tmcommonkotlin.customviews.vm_item

import android.content.Context
import android.widget.Button
import com.tminus1010.tmcommonkotlin.customviews.IHasToViewItemRecipe
import com.tminus1010.tmcommonkotlin.customviews.IViewItemRecipe3
import com.tminus1010.tmcommonkotlin.customviews.ViewItemRecipe3
import com.tminus1010.tmcommonkotlin.customviews.extensions.bind
import com.tminus1010.tmcommonkotlin.androidx.databinding.ItemButtonBinding
import com.tminus1010.tmcommonkotlin.androidx.extensions.getColorByAttr
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow

data class ButtonVMItem(
    val title: String? = null,
    val titleObservable: Observable<String>? = null,
    val titleTextSize: Float? = null,
    val isEnabled: Observable<Boolean>? = null,
    val isEnabled2: Flow<Boolean>? = null,
    val alpha: Flow<Float>? = null,
    val backgroundColor: Int? = null,
    val menuVMItems: MenuVMItems? = null,
    val menuVMItemsFlow: Flow<MenuVMItems>? = null,
    val onLongClick: (() -> Unit)? = null,
    val onClick: () -> Unit,
) : IHasToViewItemRecipe {
    fun bind(button: Button) {
        button.text = title
        if (titleObservable != null)
            button.bind(titleObservable) { text = title }
        if (titleTextSize != null)
            button.textSize = titleTextSize
        if (isEnabled != null)
            button.bind(isEnabled) { button.isEnabled = it }
        if (isEnabled2 != null)
            button.bind(isEnabled2) { button.isEnabled = it }
        if (alpha != null)
            button.bind(alpha) { alpha = it }
        if (backgroundColor != null)
            button.setBackgroundColor(button.context.theme.getColorByAttr(backgroundColor))
        menuVMItems?.bind(button)
        if (menuVMItemsFlow != null)
            button.bind(menuVMItemsFlow) { it.bind(this) }
        button.setOnClickListener { onClick() }
        onLongClick?.also { button.setOnLongClickListener { it(); true } }
    }

    override fun toViewItemRecipe(context: Context): IViewItemRecipe3 {
        return ViewItemRecipe3(context, ItemButtonBinding::inflate, ItemButtonBinding::inflate) { vb ->
            bind(vb.btn)
        }
    }
}