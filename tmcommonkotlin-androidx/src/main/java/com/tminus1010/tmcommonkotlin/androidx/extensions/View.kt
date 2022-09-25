package com.tminus1010.tmcommonkotlin.androidx.extensions

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.tminus1010.tmcommonkotlin.androidx.R
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable

fun View.easySetHeight(height: Int) =
    this.apply { easyGetLayoutParams().height = height }

fun View.easySetWidth(width: Int) =
    this.apply { easyGetLayoutParams().width = width }

fun View.easyGetLayoutParams() =
    this.layoutParams ?: ViewGroup.LayoutParams(-1, -1).also { this.layoutParams = it }

fun View.measureUnspecified() =
    measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)

fun View.measureExact() =
    measure(View.MeasureSpec.EXACTLY, View.MeasureSpec.EXACTLY)

/**
 * Experimental
 */
fun View.widthObservable(): Observable<Int> {
    return Observable.create<Int> { downstream ->
        val onLayoutChangeListener = View.OnLayoutChangeListener { _: View, left, _, right, _, _, _, _, _ ->
            downstream.onNext(right - left)
        }
        downstream.onNext(right - left)
        addOnLayoutChangeListener(onLayoutChangeListener)
        downstream.setCancellable { removeOnLayoutChangeListener(onLayoutChangeListener) }
    }.subscribeOn(AndroidSchedulers.mainThread()) // This might not be necessary
        .filter { it != 0 } // I'm not sure why, but sometimes this emits 0
        .distinctUntilChanged()
}

fun View.addOnFocusChangeListenerDecoration(onFocusChangeListener: View.OnFocusChangeListener) {
    val oldOnFocusChangeListener = this.onFocusChangeListener
    this.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
        onFocusChangeListener.onFocusChange(v, hasFocus)
        oldOnFocusChangeListener?.onFocusChange(v, hasFocus)
    }
}

var View.lifecycleOwner: LifecycleOwner?
    get() = findViewTreeLifecycleOwner()
    set(value) {
        setTag(androidx.lifecycle.runtime.R.id.view_tree_lifecycle_owner, value)
    }

/**
 * This will only be true if [removeAllViewsAndSetTag] was called.
 */
var View.isRemovingViews: Boolean
    get() = getTag(R.id.tag_is_removing_view) as? Boolean ?: false
    set(value) {
        setTag(R.id.tag_is_removing_view, value)
    }

val View.parents
    get() = sequence<View> {
        var x = parent
        while (x as? View != null) {
            yield(x)
            x = (x as? View)?.parent
        }
    }