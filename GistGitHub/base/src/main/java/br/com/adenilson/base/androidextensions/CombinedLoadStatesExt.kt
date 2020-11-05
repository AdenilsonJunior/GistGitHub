package br.com.adenilson.base.androidextensions

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState

fun CombinedLoadStates.isSourceLoading(): Boolean {
    return source.append == LoadState.Loading ||
            source.refresh == LoadState.Loading
}

fun CombinedLoadStates.isSourceError(
    refreshError: ((Throwable) -> Unit)? = null,
    appendError: ((Throwable) -> Unit)? = null
) {
    with(source.refresh) {
        if (this is LoadState.Error) {
            refreshError?.invoke(error)
        }
    }

    with(source.append) {
        if (this is LoadState.Error) {
            appendError?.invoke(error)
        }
    }
}