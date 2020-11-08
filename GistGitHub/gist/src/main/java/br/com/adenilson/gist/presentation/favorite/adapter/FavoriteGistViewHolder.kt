package br.com.adenilson.gist.presentation.favorite.adapter

import android.view.View
import br.com.adenilson.base.androidextensions.loadFromUrl
import br.com.adenilson.base.presentation.adapter.AbstractViewHolder
import br.com.adenilson.base.presentation.adapter.ViewTypesListener
import br.com.adenilson.gist.R
import br.com.adenilson.data.model.GistModel
import br.com.adenilson.gist.presentation.model.Gist
import kotlinx.android.synthetic.main.item_favorite_gist.view.imageViewAvatar
import kotlinx.android.synthetic.main.item_favorite_gist.view.textViewGistName
import kotlinx.android.synthetic.main.item_favorite_gist.view.textViewUserName

class FavoriteGistViewHolder(val view: View, val listener: ViewTypesListener<Gist>, val favoriteClickListener: ViewTypesListener<Gist>) : AbstractViewHolder<Gist>(view) {

    override fun bind(item: Gist) {
        with(view) {
            textViewGistName.text = item.gistType
            textViewUserName.text = item.owner.name
            imageViewAvatar.loadFromUrl(item.owner.avatarUrl, R.drawable.placeholder_user)
        }

        itemView.setOnClickListener { listener(item) }
    }
}