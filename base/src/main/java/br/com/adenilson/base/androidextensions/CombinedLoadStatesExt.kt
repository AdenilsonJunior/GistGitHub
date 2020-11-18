package br.com.adenilson.base.androidextensions

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState

fun CombinedLoadStates.checkSourceLoadState(
    notLoading: (() -> Unit)? = null,
    refreshLoading: (() -> Unit)? = null,
    appendLoading: (() -> Unit)? = null,
    refreshError: ((Throwable) -> Unit)? = null,
    appendError: ((Throwable) -> Unit)? = null
) {
    if (source.refresh is LoadState.NotLoading && source.append is LoadState.NotLoading) {
        notLoading?.invoke()
    }

    with(source.refresh) {
        if (this is LoadState.Loading) {
            refreshLoading?.invoke()
        }
        if (this is LoadState.Error) {
            refreshError?.invoke(error)
        }
    }

    with(source.append) {
        if (this is LoadState.Loading) {
            appendLoading?.invoke()
        }
        if (this is LoadState.Error) {
            appendError?.invoke(error)
        }
    }
}