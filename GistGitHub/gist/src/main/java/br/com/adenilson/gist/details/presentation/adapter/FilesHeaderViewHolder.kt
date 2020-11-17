package br.com.adenilson.gist.details.presentation.adapter

import android.view.View
import br.com.adenilson.base.presentation.adapter.AbstractViewHolder
import br.com.adenilson.gist.R
import kotlinx.android.synthetic.main.item_file_header.view.textViewTitle

class FilesHeaderViewHolder(val view: View) : AbstractViewHolder<FilesHeaderItem>(view) {
    override fun bind(item: FilesHeaderItem) {
        with(view) {
            textViewTitle.text =
                resources.getQuantityString(
                    R.plurals.gist_files_header_count,
                    item.count,
                    item.count
                )
        }
    }
}