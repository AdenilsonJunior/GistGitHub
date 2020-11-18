package br.com.adenilson.gist.list.presentation.adapter

import android.view.View
import br.com.adenilson.base.androidextensions.loadFromUrl
import br.com.adenilson.base.presentation.adapter.AbstractViewHolder
import br.com.adenilson.base.presentation.adapter.ViewTypesListener
import br.com.adenilson.gist.R
import br.com.adenilson.gist.common.domain.model.Gist
import kotlinx.android.synthetic.main.item_gist.view.imageViewAvatar
import kotlinx.android.synthetic.main.item_gist.view.imageViewFavorite
import kotlinx.android.synthetic.main.item_gist.view.textViewFilesCount
import kotlinx.android.synthetic.main.item_gist.view.textViewGistType
import kotlinx.android.synthetic.main.item_gist.view.textViewUserName

class GistViewHolder(
    private val view: View,
    private val viewTypesListener: ViewTypesListener<Gist>,
    private val favoriteClickListener: ViewTypesListener<Gist>
) : AbstractViewHolder<Gist>(view) {

    override fun bind(item: Gist) {
        with(view) {
            textViewUserName.text = item.owner.name
            imageViewAvatar.loadFromUrl(item.owner.avatarUrl, R.drawable.placeholder_user)
            textViewFilesCount.text =
                context.resources.getQuantityString(R.plurals.gist_files_count, item.files.size, item.files.size)
            textViewGistType.text = item.gistType
            itemView.setOnClickListener {
                viewTypesListener.invoke(item)
            }
            if (item.favorite) {
                imageViewFavorite.setImageResource(R.drawable.ic_star)
            } else {
                imageViewFavorite.setImageResource(R.drawable.ic_empty_star)
            }
            imageViewFavorite.setOnClickListener {
                favoriteClickListener(item)
            }
        }
    }
}