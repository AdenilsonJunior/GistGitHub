package br.com.adenilson.gist.presentation.details.adapter

import android.view.View
import br.com.adenilson.base.androidextensions.hide
import br.com.adenilson.base.androidextensions.loadFromUrl
import br.com.adenilson.base.androidextensions.show
import br.com.adenilson.base.presentation.adapter.AbstractViewHolder
import br.com.adenilson.gist.R
import kotlinx.android.synthetic.main.item_gist.view.imageViewAvatar
import kotlinx.android.synthetic.main.item_gist_header.view.textViewDescription
import kotlinx.android.synthetic.main.item_gist_header.view.textViewGistType
import kotlinx.android.synthetic.main.item_gist_header.view.textViewUserName

class HeaderViewHolder(private val view: View) : AbstractViewHolder<HeaderItem>(view) {

    override fun bind(item: HeaderItem) {
        with(view) {
            imageViewAvatar.loadFromUrl(item.avatar, R.drawable.placeholder_user)
            textViewUserName.text = item.userName
            textViewGistType.text = item.gistType

            item.description?.run {
                textViewDescription.show()
                textViewDescription.text = this
            } ?: textViewDescription.hide()
        }
    }
}