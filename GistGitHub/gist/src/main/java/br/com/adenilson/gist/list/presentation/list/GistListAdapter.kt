package br.com.adenilson.gist.list.presentation.list

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import br.com.adenilson.base.androidextensions.inflate
import br.com.adenilson.base.domain.exception.InvalidViewTypeException
import br.com.adenilson.base.presentation.adapter.AbstractViewHolder
import br.com.adenilson.base.presentation.adapter.ViewTypesFactory
import br.com.adenilson.base.presentation.adapter.ViewTypesListener
import br.com.adenilson.gist.R
import br.com.adenilson.gist.list.domain.model.Gist

class GistListAdapter(private val listener: ViewTypesListener<Gist>) :
    PagingDataAdapter<Gist, AbstractViewHolder<Gist>>(DIFF_UTIL) {

    private val factory = GistListFactory()

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder<Gist> {
        return parent.inflate(viewType).let { view ->
            factory.holder(viewType, view, listener) as AbstractViewHolder<Gist>
        }
    }

    override fun onBindViewHolder(holder: AbstractViewHolder<Gist>, position: Int) {
        getItem(position)?.let { gist ->
            holder.bind(gist)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.let { gist -> factory.type(gist) }
            ?: throw InvalidViewTypeException()
    }

    private class GistListFactory : ViewTypesFactory<Gist> {
        override fun type(model: Gist): Int {
            return R.layout.item_gist
        }

        override fun holder(
            type: Int,
            view: View,
            listener: ViewTypesListener<Gist>
        ): AbstractViewHolder<*> {
            return GistViewHolder(view, listener)
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Gist>() {
            override fun areItemsTheSame(oldItem: Gist, newItem: Gist): Boolean {
                return oldItem.webId == newItem.webId
            }

            override fun areContentsTheSame(oldItem: Gist, newItem: Gist): Boolean {
                return oldItem == newItem
            }
        }
    }
}