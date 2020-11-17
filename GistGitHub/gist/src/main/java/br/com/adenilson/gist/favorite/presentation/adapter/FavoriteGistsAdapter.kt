package br.com.adenilson.gist.favorite.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.adenilson.base.androidextensions.inflate
import br.com.adenilson.base.presentation.adapter.AbstractViewHolder
import br.com.adenilson.base.presentation.adapter.ViewTypesFactory
import br.com.adenilson.base.presentation.adapter.ViewTypesListener
import br.com.adenilson.gist.R
import br.com.adenilson.gist.commons.domain.model.Gist

class FavoriteGistsAdapter(
    private val viewTypesListener: ViewTypesListener<Gist>,
    private val favoriteClickListener: ViewTypesListener<Gist>
) : RecyclerView.Adapter<AbstractViewHolder<Gist>>() {

    var data: List<Gist> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val factory = FavoriteGistsFactory()

    override fun onBindViewHolder(holder: AbstractViewHolder<Gist>, position: Int) {
        holder.bind(data[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder<Gist> {
        return parent.inflate(viewType).let { view ->
            factory.holder(viewType, view, viewTypesListener, favoriteClickListener)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return factory.type(data[position])
    }

    fun updateFavoriteGist(gist: Gist) {
        val index = data.indexOfFirst { it.id == gist.id }
        notifyItemChanged(index)
    }

    private class FavoriteGistsFactory : ViewTypesFactory<Gist> {

        override fun type(model: Gist): Int {
            return R.layout.item_favorite_gist
        }

        override fun holder(
            type: Int,
            view: View,
            vararg listener: ViewTypesListener<Gist>
        ): AbstractViewHolder<Gist> {
            return FavoriteGistViewHolder(view, listener[0], listener[1])
        }
    }
}