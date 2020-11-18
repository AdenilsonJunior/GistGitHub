package br.com.adenilson.gist.details.presentation.adapter

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.com.adenilson.gist.R

class DetailsSpaceItemDecoration(private val context: Context) : RecyclerView.ItemDecoration() {

    companion object {
        private const val HEADER_POSITION = 0
        private const val FILE_HEADER_POSITION = 1
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(context) {
            val childPosition = parent.getChildAdapterPosition(view)
            if (childPosition > HEADER_POSITION) {

                if (childPosition != FILE_HEADER_POSITION) {
                    outRect.top = resources.getDimensionPixelSize(R.dimen.details_item_top)
                }
                outRect.left = resources.getDimensionPixelSize(R.dimen.details_item_start)
                outRect.right = resources.getDimensionPixelSize(R.dimen.details_item_end)

                if (childPosition == state.itemCount - 1) {
                    outRect.bottom = resources.getDimensionPixelSize(R.dimen.details_item_bottom)
                }
            }
        }
    }
}