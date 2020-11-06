package br.com.adenilson.gist.presentation.details.adapter

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.com.adenilson.base.androidextensions.dpToPx

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

                if(childPosition != FILE_HEADER_POSITION) {
                    outRect.top = 16.dpToPx(this)
                }
                outRect.left = 24.dpToPx(this)
                outRect.right = 24.dpToPx(this)

                if (childPosition == state.itemCount - 1) {
                    outRect.bottom = 16.dpToPx(this)
                }
            }
        }
    }
}