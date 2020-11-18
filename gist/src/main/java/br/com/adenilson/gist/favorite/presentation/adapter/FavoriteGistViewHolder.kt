package br.com.adenilson.gist.favorite.presentation.adapter

import android.view.View
import br.com.adenilson.base.androidextensions.loadFromUrl
import br.com.adenilson.base.presentation.adapter.AbstractViewHolder
import br.com.adenilson.base.presentation.adapter.ViewTypesListener
import br.com.adenilson.gist.R
import br.com.adenilson.gist.common.domain.model.Gist
import kotlinx.android.synthetic.main.item_favorite_gist.view.imageViewAvatar
import kotlinx.android.synthetic.main.item_favorite_gist.view.imageViewFavorite
import kotlinx.android.synthetic.main.item_favorite_gist.view.textViewGistName
import kotlinx.android.synthetic.main.item_favorite_gist.view.textViewUserName

class FavoriteGistViewHolder(val view: View, val listener: ViewTypesListener<Gist>, val favoriteClickListener: ViewTypesListener<Gist>) : AbstractViewHolder<Gist>(view) {

    override fun bind(item: Gist) {
        with(view) {
            textViewGistName.text = item.gistType
            textViewUserName.text = item.owner.name
            imageViewAvatar.loadFromUrl(item.owner.avatarUrl, R.drawable.placeholder_user)
            if (item.favorite) {
                imageViewFavorite.setImageResource(R.drawable.ic_star)
            } else {
                imageViewFavorite.setImageResource(R.drawable.ic_empty_star)
            }
            imageViewFavorite.setOnClickListener {
                favoriteClickListener(item)
            }
        }

        itemView.setOnClickListener { listener(item) }
    }
}