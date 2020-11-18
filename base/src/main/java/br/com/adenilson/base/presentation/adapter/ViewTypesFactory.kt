package br.com.adenilson.base.presentation.adapter

import android.view.View

interface ViewTypesFactory<T> {
    fun type(model: T): Int
    fun holder(type: Int, view: View, vararg listener: ViewTypesListener<T>): AbstractViewHolder<*>
}

typealias ViewTypesListener<T> = (T) -> Unit