package br.com.adenilson.gist.list.presentation.list

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.adenilson.base.androidextensions.inflate
import br.com.adenilson.base.presentation.adapter.AbstractViewHolder
import br.com.adenilson.base.presentation.adapter.ViewTypesFactory
import br.com.adenilson.base.presentation.adapter.ViewTypesListener
import br.com.adenilson.gist.R
import br.com.adenilson.gist.list.domain.model.Gist

class GistListAdapter(private val listener: ViewTypesListener<Gist>) :
    RecyclerView.Adapter<AbstractViewHolder<Gist>>() {

    var data: MutableList<Gist> = mutableListOf()
        set(data) {
            field = data
            notifyDataSetChanged()
        }

    private val factory = GistListFactory()

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder<Gist> {
        return parent.inflate(viewType).let { view ->
            factory.holder(viewType, view, listener) as AbstractViewHolder<Gist>
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: AbstractViewHolder<Gist>, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemViewType(position: Int): Int {
        return factory.type(data[position])
    }

    fun addItems(gists: List<Gist>) {
        val currentSize = data.size
        data.addAll(gists)
        notifyItemRangeInserted(currentSize, data.size)
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
}