package com.tminus1010.tmcommonkotlin.misc

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * This live data will emit each item once, even if multiple observers are observing.
 *
 * It is useful if you want a toast or error message to display only once, even if observing from multiple fragments.
 */
class LiveEvent<T>(source: LiveData<T>? = null) : MediatorLiveData<T>() {
    private val pending = AtomicBoolean(false)

    init {
        if (source != null) addSource(source) { value = it }
    }

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner) {
            if (pending.compareAndSet(true, false))
                observer.onChanged(it)
        }
    }

    @MainThread
    override fun setValue(t: T?) {
        pending.set(true)
        super.setValue(t)
    }

    fun clear() {
        value = null
    }
}