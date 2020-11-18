package br.com.adenilson.gist.details.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.adenilson.base.androidextensions.inflate
import br.com.adenilson.base.domain.exception.InvalidViewTypeException
import br.com.adenilson.base.presentation.adapter.AbstractViewHolder
import br.com.adenilson.base.presentation.adapter.ViewTypesFactory
import br.com.adenilson.base.presentation.adapter.ViewTypesListener
import br.com.adenilson.gist.R

class GistDetailsAdapter(private val viewTypesListener: ViewTypesListener<GistDetailsItem>) :
    RecyclerView.Adapter<AbstractViewHolder<GistDetailsItem>>() {

    var data: List<GistDetailsItem> = listOf()
        set(data) {
            field = data
            notifyDataSetChanged()
        }

    private val factory = GistDetailsFactory()

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbstractViewHolder<GistDetailsItem> {
        return parent.inflate(viewType).let { view ->
            factory.holder(viewType, view, viewTypesListener) as AbstractViewHolder<GistDetailsItem>
        }
    }

    override fun onBindViewHolder(holder: AbstractViewHolder<GistDetailsItem>, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    override fun getItemViewType(position: Int): Int {
        return factory.type(data[position])
    }

    private class GistDetailsFactory :
        ViewTypesFactory<GistDetailsItem> {

        override fun type(model: GistDetailsItem): Int {
            return when (model) {
                is HeaderItem -> R.layout.item_gist_header
                is FilesHeaderItem -> R.layout.item_file_header
                is FileItem -> R.layout.item_file
                is HtmlUrlItem -> R.layout.item_html_url
                else -> throw InvalidViewTypeException()
            }
        }

        override fun holder(
            type: Int,
            view: View,
            vararg listener: ViewTypesListener<GistDetailsItem>
        ): AbstractViewHolder<*> {
            return when (type) {
                R.layout.item_gist_header -> HeaderViewHolder(view)
                R.layout.item_file_header -> FilesHeaderViewHolder(view)
                R.layout.item_file -> FileViewHolder(view, listener[0])
                R.layout.item_html_url -> HtmlUrlViewHolder(view, listener[0])
                else -> throw InvalidViewTypeException()
            }
        }
    }
}