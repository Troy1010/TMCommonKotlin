package com.tminus1010.tmcommonkotlin.misc.extensions

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.tminus1010.tmcommonkotlin.coroutines.extensions.observe
import com.tminus1010.tmcommonkotlin.rx3.extensions.observe
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow


inline fun <V : View, T : Any> V.bind(observable: Observable<T>, lifecycle: LifecycleOwner? = null, crossinline lambda: V.(T) -> Unit) {
    val lifecycleRedef =
        lifecycle ?: findViewTreeLifecycleOwner()
        ?: error("Could not find lifecycle. This might happen in Recyclerviews or other unattached views.\nEither add a lifecycle to the view, attach to a view with a lifecycle, or specify a lifecycle as argument.")
    observable.observe(lifecycleRedef) { lambda(it) }
}

inline fun <V : View, reified T : Any> V.bind(flow: Flow<T>, lifecycle: LifecycleOwner? = null, crossinline lambda: V.(T) -> Unit) {
    val lifecycleRedef =
        lifecycle ?: findViewTreeLifecycleOwner()
        ?: error("Could not find lifecycle. This might happen in Recyclerviews or other unattached views.\nEither add a lifecycle to the view, attach to a view with a lifecycle, or specify a lifecycle as argument.")
    flow.observe(lifecycleRedef) { lambda(it) }
}

var View.lifecycleOwner: LifecycleOwner?
    get() = findViewTreeLifecycleOwner()
    set(value) {
        setTag(androidx.lifecycle.runtime.R.id.view_tree_lifecycle_owner, value)
    }