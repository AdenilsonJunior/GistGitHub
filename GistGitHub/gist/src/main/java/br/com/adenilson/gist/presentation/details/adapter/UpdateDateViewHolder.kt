package br.com.adenilson.gist.presentation.details.adapter

import android.view.View
import br.com.adenilson.core.extensions.parseToString
import br.com.adenilson.base.presentation.adapter.AbstractViewHolder
import br.com.adenilson.gist.R
import kotlinx.android.synthetic.main.item_update_date.view.textViewLastUpdate

class UpdateDateViewHolder(private val view: View) : AbstractViewHolder<UpdateDateItem>(view) {
    override fun bind(item: UpdateDateItem) {
        with(view) {
            textViewLastUpdate.text = context.getString(R.string.gist_last_update, item.date.parseToString())
        }
    }
}