package br.com.adenilson.gist.details.presentation.adapter

import android.view.View
import br.com.adenilson.base.presentation.adapter.AbstractViewHolder
import br.com.adenilson.base.presentation.adapter.ViewTypesListener
import br.com.adenilson.gist.R
import kotlinx.android.synthetic.main.item_html_url.view.textViewHtmlUrl

class HtmlUrlViewHolder(
    private val view: View,
    private val listener: ViewTypesListener<HtmlUrlItem>
) : AbstractViewHolder<HtmlUrlItem>(view) {
    override fun bind(item: HtmlUrlItem) {
        with(view) {
            textViewHtmlUrl.text = context.getString(R.string.gist_html_url, item.url)
        }
        itemView.setOnClickListener { listener(item) }
    }
}