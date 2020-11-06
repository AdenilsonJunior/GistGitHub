package br.com.adenilson.gist.presentation.details.adapter

import android.view.View
import br.com.adenilson.base.androidextensions.hide
import br.com.adenilson.base.androidextensions.show
import br.com.adenilson.base.presentation.adapter.AbstractViewHolder
import br.com.adenilson.base.presentation.adapter.ViewTypesListener
import br.com.adenilson.gist.R
import kotlinx.android.synthetic.main.item_file.view.textViewFilename
import kotlinx.android.synthetic.main.item_file.view.textViewLanguage
import kotlinx.android.synthetic.main.item_file.view.textViewSize
import kotlinx.android.synthetic.main.item_file.view.textViewType

class FileViewHolder(private val view: View, val listener: ViewTypesListener<FileItem>) :
    AbstractViewHolder<FileItem>(view) {

    override fun bind(item: FileItem) {
        with(view) {
            textViewFilename.text = item.filename
            textViewType.text = item.type
            textViewSize.text = context.getString(R.string.gist_text_file_size, item.size)
            item.language?.run {
                textViewLanguage.show()
                textViewLanguage.text = this
            } ?: textViewLanguage.hide()
        }
        itemView.setOnClickListener { listener(item) }
    }
}