package kotlin
// ^ packages that start with "kotlin" are automatically imported

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach

inline fun <reified T> Flow<T>.doLogx(prefix: String? = null, crossinline transform: (x: T) -> Any? = { it }, z: Unit = Unit): Flow<T> {
    return onEach { transform(it).logx(prefix) }
        .onCompletion { if (it == null) "Completed".logx(prefix) else logz("$prefix`Error:", it) }
}