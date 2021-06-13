package androidx.lifecycle

import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.io.Closeable

private const val KEY_DISPOSABLES = "tmcommonkotlin.KEY_DISPOSABLES"

/**
 * will be disposed when [ViewModel.onCleared]
 */
val ViewModel.disposables: CompositeDisposable
    get() = (getTag(KEY_DISPOSABLES) as? CloseableCompositeDisposable)?.compositeDisposable
        ?: CloseableCompositeDisposable()
            .also { setTagIfAbsent(KEY_DISPOSABLES, it) }
            .compositeDisposable

/**
 * Values in a ViewModel store will .close() when [ViewModel.onCleared].
 * We also want that value to provide a CompositeDisposable, so this class does both.
 */
internal class CloseableCompositeDisposable : Closeable {
    val compositeDisposable = CompositeDisposable()
    override fun close() = compositeDisposable.dispose()
}