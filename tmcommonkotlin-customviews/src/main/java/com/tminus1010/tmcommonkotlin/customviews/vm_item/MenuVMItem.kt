package com.tminus1010.tmcommonkotlin.customviews.vm_item

import com.tminus1010.tmcommonkotlin.view.NativeText

data class MenuVMItem(
    val text: NativeText,
    val onClick: () -> Unit,
)