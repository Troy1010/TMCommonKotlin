package com.tminus1010.exampleclient.ui.all_features.vm_item

import android.view.Menu
import android.view.View

data class MenuVMItems(
    private val menuVMItems: List<MenuVMItem>
) {
    constructor(vararg menuVMItems: MenuVMItem?) : this(menuVMItems.toList().filterNotNull())

    fun bind(menu: Menu) {
        menu.clear()
        menuVMItems.forEach { menuItemPartial ->
            menu.add(menuItemPartial.title)
                .setOnMenuItemClickListener { menuItemPartial.onClick(); true }
        }
    }

    fun bind(view: View) {
        view.setOnCreateContextMenuListener { menu, _, _ -> bind(menu) }
    }
}