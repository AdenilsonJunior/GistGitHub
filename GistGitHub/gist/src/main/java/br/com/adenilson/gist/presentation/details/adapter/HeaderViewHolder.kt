package br.com.adenilson.gist.presentation.details.adapter

import android.view.View
import br.com.adenilson.base.presentation.adapter.AbstractViewHolder
import br.com.adenilson.gist.databinding.ItemGistHeaderBinding

class HeaderViewHolder(private val view: View) : AbstractViewHolder<HeaderItem>(view) {

    private val itemBinding by lazy {
        ItemGistHeaderBinding.bind(view)
    }

    override fun bind(item: HeaderItem) {
        itemBinding.header = item
    }
}