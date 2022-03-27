package com.tminus1010.tmcommonkotlin.view

import android.content.Context
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

sealed class NativeText {
    abstract fun toCharSequence(context: Context): CharSequence

    data class Simple(val text: String) : NativeText() {
        override fun toCharSequence(context: Context): CharSequence {
            return text
        }
    }

    data class Resource(@StringRes val id: Int) : NativeText() {
        override fun toCharSequence(context: Context): CharSequence {
            return context.getString(id)
        }
    }

    data class Plural(@PluralsRes val id: Int, val number: Int, val args: List<Any>) : NativeText() {
        constructor(@PluralsRes id: Int, number: Int, vararg args: Any) : this(id, number, args.toList())

        override fun toCharSequence(context: Context): CharSequence {
            return context.resources.getQuantityString(id, number, *args.toTypedArray())
        }
    }

    data class Arguments(@StringRes val id: Int, val args: List<Any>) : NativeText() {
        constructor(@StringRes id: Int, vararg args: Any) : this(id, args.toList())

        override fun toCharSequence(context: Context): CharSequence {
            return context.getString(id, *args.toTypedArray())
        }
    }

    data class Multi(val nativeTexts: List<NativeText>) : NativeText() {
        constructor(vararg nativeTexts: NativeText) : this(nativeTexts.toList())

        override fun toCharSequence(context: Context): CharSequence {
            return StringBuilder()
                .apply { nativeTexts.forEach { append(it.toCharSequence(context)) } }
                .toString()
        }
    }
}