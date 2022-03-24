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
        override fun toCharSequence(context: Context): CharSequence {
            return context.resources.getQuantityString(id, number, *args.toTypedArray())
        }
    }

    data class Arguments(@StringRes val id: Int, val args: List<Any>) : NativeText() {
        override fun toCharSequence(context: Context): CharSequence {
            return context.getString(id, *args.toTypedArray())
        }
    }

    data class Multi(val nativeTexts: List<NativeText>) : NativeText() {
        override fun toCharSequence(context: Context): CharSequence {
            return StringBuilder()
                .apply { nativeTexts.forEach { append(it.toCharSequence(context)) } }
                .toString()
        }
    }
}