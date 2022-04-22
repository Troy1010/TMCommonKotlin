package com.tminus1010.tmcommonkotlin.customviews.vm_item

data class MenuVMItem(
    val title: String,
    val onClick: () -> Unit
)