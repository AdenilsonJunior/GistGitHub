package br.com.adenilson.gist.list.presentation.list

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.com.adenilson.base.androidextensions.dpToPx

class SpaceItemDecoration(private val context: Context) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(context) {
            outRect.top = 16.dpToPx(this)
            outRect.left = 24.dpToPx(this)
            outRect.right = 24.dpToPx(this)

            if (parent.getChildAdapterPosition(view) == state.itemCount - 1) {
                outRect.bottom = 16.dpToPx(this)
            }
        }
    }
}