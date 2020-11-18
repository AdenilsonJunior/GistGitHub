package br.com.adenilson.gistgithub.android.matchers

import android.content.res.Resources
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class RecyclerViewMatcher(private val recyclerViewId: Int) {

    fun hasView(targetViewId: Int): Matcher<View?>? {
        return object : TypeSafeMatcher<View?>() {
            var resources: Resources? = null
            var itemsView: MutableList<View?> = mutableListOf()

            override fun describeTo(description: Description?) {
                var idDescription = recyclerViewId.toString()
                if (resources != null) {
                    idDescription = try {
                        resources?.getResourceName(recyclerViewId).orEmpty()
                    } catch (var4: Resources.NotFoundException) {
                        String.format(
                            "%s (resource name not found)",
                            *arrayOf<Any?>(Integer.valueOf(recyclerViewId))
                        )
                    }
                }
                description?.appendText("with id: $idDescription")
            }

            override fun matchesSafely(view: View?): Boolean {
                resources = view?.resources
                    val recyclerView: RecyclerView? =
                        view?.rootView?.findViewById(recyclerViewId) as? RecyclerView
                        if (recyclerView != null && recyclerView.id == recyclerViewId) {
                            for (i in 0 until recyclerView.childCount) {
                                itemsView.add(recyclerView.findViewHolderForAdapterPosition(i)?.itemView)
                            }
                        } else {
                            return false
                        }
                return if (targetViewId == -1) {
                    false
                } else {
                    return itemsView.any { it?.findViewById<View>(targetViewId) == view }
                }
            }
        }
    }

    fun atPosition(position: Int): Matcher<View?>? {
        return atPositionOnView(position, -1)
    }

    fun atPositionOnView(position: Int, targetViewId: Int): Matcher<View?>? {
        return object : TypeSafeMatcher<View?>() {
            var resources: Resources? = null
            var childView: View? = null

            override fun describeTo(description: Description?) {
                var idDescription = recyclerViewId.toString()
                if (resources != null) {
                    idDescription = try {
                        resources?.getResourceName(recyclerViewId).orEmpty()
                    } catch (var4: Resources.NotFoundException) {
                        String.format(
                            "%s (resource name not found)",
                            *arrayOf<Any?>(Integer.valueOf(recyclerViewId))
                        )
                    }
                }
                description?.appendText("with id: $idDescription")
            }

            override fun matchesSafely(view: View?): Boolean {
                resources = view?.resources
                if (childView == null) {
                    val recyclerView: RecyclerView? =
                        view?.rootView?.findViewById(recyclerViewId) as? RecyclerView
                    childView =
                        if (recyclerView != null && recyclerView.id == recyclerViewId) {
                            recyclerView.findViewHolderForAdapterPosition(position)?.itemView
                        } else {
                            return false
                        }
                }
                return if (targetViewId == -1) {
                    view == childView
                } else {
                    val targetView: View? = childView?.findViewById(targetViewId)
                    view == targetView
                }
            }
        }
    }
}