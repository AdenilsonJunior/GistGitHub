package br.com.adenilson.gist.list.presentation.adapter

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.com.adenilson.gist.R

class ListSpaceItemDecoration(private val context: Context) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(context) {
            val listSpace = resources.getDimensionPixelSize(R.dimen.list_space)
            outRect.top = listSpace
            outRect.left = listSpace
            outRect.right = listSpace

            if (parent.getChildAdapterPosition(view) == state.itemCount - 1) {
                outRect.bottom = listSpace
            }
        }
    }
}