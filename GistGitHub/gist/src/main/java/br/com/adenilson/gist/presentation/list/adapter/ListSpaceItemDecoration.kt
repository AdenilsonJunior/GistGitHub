package br.com.adenilson.gist.presentation.list.adapter

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.com.adenilson.base.androidextensions.dpToPx

class ListSpaceItemDecoration(private val context: Context) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(context) {
            outRect.top = 16.dpToPx(this)
            outRect.left = 16.dpToPx(this)
            outRect.right = 16.dpToPx(this)

            if (parent.getChildAdapterPosition(view) == state.itemCount - 1) {
                outRect.bottom = 16.dpToPx(this)
            }
        }
    }
}