package com.tminus1010.tmcommonkotlin.customviews

import android.content.Context

interface ViewItemRecipeFactory {
    fun toViewItemRecipe(context: Context): IViewItemRecipe3
}
