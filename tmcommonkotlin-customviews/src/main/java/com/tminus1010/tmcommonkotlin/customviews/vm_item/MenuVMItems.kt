package com.tminus1010.tmcommonkotlin.customviews.vm_item

import android.content.Context
import android.view.Menu
import android.view.View

data class MenuVMItems(
    private val menuVMItems: List<MenuVMItem>
) {
    constructor(vararg menuVMItems: MenuVMItem?) : this(menuVMItems.toList().filterNotNull())

    fun bind(menu: Menu, context: Context) {
        menu.clear()
        menuVMItems.forEach { menuVMItem ->
            menu.add(menuVMItem.text.toCharSequence(context))
                .setOnMenuItemClickListener { menuVMItem.onClick(); true }
        }
    }

    fun bind(view: View) {
        view.setOnCreateContextMenuListener { menu, _, _ -> bind(menu, view.context) }
    }
}