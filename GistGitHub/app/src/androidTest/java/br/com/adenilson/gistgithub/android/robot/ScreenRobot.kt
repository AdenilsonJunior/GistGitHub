package br.com.adenilson.gistgithub.android.robot

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.Toolbar
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import br.com.adenilson.gistgithub.android.matchers.RecyclerViewMatcher
import junit.framework.Assert.assertTrue
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

abstract class ScreenRobot<T : ScreenRobot<T>> {

    private var activityContext: Activity? = null

    fun checkRecyclerViewHasNoItem(
        @IdRes recyclerView: Int,
        @IdRes view: Int
    ): T {
        try {
            Espresso.onView(
                RecyclerViewMatcher(recyclerView)
                    .hasView(view)
            )
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            assertTrue(false)
        } catch (e: NoMatchingViewException) {
            assertTrue(true)
        }
        return this as T
    }

    fun checkRecyclerViewItemIsDisplayed(
        @IdRes recyclerView: Int,
        atPosition: Int = -1,
        @IdRes view: Int
    ): T {
        Espresso.onView(
            RecyclerViewMatcher(recyclerView)
                .atPositionOnView(atPosition, view)
        )
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        return this as T
    }

    fun clickOnSpinnerOption(withText: String): T {
        Espresso.onData(
            Matchers.allOf(
                Matchers.`is`(Matchers.instanceOf(String::class.java)),
                Matchers.`is`(withText)
            )
        ).perform(ViewActions.click())
        return this as T
    }

    fun checkIsDisabled(@IdRes vararg viewIds: Int): T {
        for (viewId in viewIds) {
            Espresso.onView(ViewMatchers.withId(viewId))
                .check(
                    ViewAssertions.matches(
                        Matchers.allOf(
                            Matchers.not(ViewMatchers.isEnabled()),
                            Matchers.not(ViewMatchers.isClickable())
                        )
                    )
                )
        }
        return this as T
    }

    fun checkIsEnabled(@IdRes vararg viewIds: Int): T {
        for (viewId in viewIds) {
            Espresso.onView(ViewMatchers.withId(viewId))
                .check(
                    ViewAssertions.matches(
                        Matchers.allOf(
                            ViewMatchers.isEnabled(),
                            ViewMatchers.isClickable()
                        )
                    )
                )
        }
        return this as T
    }

    fun checkIsDisplayed(@IdRes vararg viewIds: Int): T {
        for (viewId in viewIds) {
            Espresso.onView(ViewMatchers.withId(viewId))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }
        return this as T
    }

    fun checkIsHidden(@IdRes vararg viewIds: Int): T {
        for (viewId in viewIds) {
            Espresso.onView(ViewMatchers.withId(viewId))
                .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.isDisplayed())))
        }
        return this as T
    }

    fun checkViewHasText(@IdRes viewId: Int, expected: String): T {
        Espresso.onView(ViewMatchers.withId(viewId))
            .check(ViewAssertions.matches(ViewMatchers.withText(expected)))
        return this as T
    }

    fun checkViewHasText(@IdRes viewId: Int, @StringRes messageResId: Int): T {
        Espresso.onView(ViewMatchers.withId(viewId))
            .check(ViewAssertions.matches(ViewMatchers.withText(messageResId)))
        return this as T
    }

    fun checkViewHasHint(@IdRes viewId: Int, @StringRes messageResId: Int): T {
        Espresso.onView(ViewMatchers.withId(viewId))
            .check(ViewAssertions.matches(ViewMatchers.withHint(messageResId)))
        return this as T
    }

    fun clickOkOnView(@IdRes viewId: Int): T {
        Espresso.onView(ViewMatchers.withId(viewId))
            .perform(ViewActions.click())
        return this as T
    }

    fun clickOkOnView(matcher: Matcher<View>): T {
        Espresso.onView(matcher)
            .perform(ViewActions.click())
        return this as T
    }

    fun enterTextIntoView(@IdRes viewId: Int, text: String): T {
        Espresso.onView(ViewMatchers.withId(viewId))
            .perform(ViewActions.typeText(text))
        return this as T
    }

    fun provideContext(scenario: ActivityScenario<out Activity>): T {
        scenario.onActivity {
            this.activityContext = it
        }
        return this as T
    }

    fun checkDialogWithTextIsDisplayed(@StringRes messageResId: Int): T {
        Espresso.onView(ViewMatchers.withText(messageResId))
            .inRoot(RootMatchers.withDecorView(CoreMatchers.not(activityContext!!.window.decorView)))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        return this as T
    }

    fun swipeLeftOnView(@IdRes viewId: Int): T {
        Espresso.onView(ViewMatchers.withId(viewId))
            .perform(ViewActions.swipeLeft())
        return this as T
    }

    fun clickBackPressed(): T {
        Espresso.onView(ViewMatchers.isRoot())
            .perform(ViewActions.pressBack())
        return this as T
    }

    fun clickHomeButton() {
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.isAssignableFrom(AppCompatImageButton::class.java),
                ViewMatchers.withParent(ViewMatchers.isAssignableFrom(Toolbar::class.java))
            )
        )
            .perform(ViewActions.click())
    }

    companion object {

        fun <T : ScreenRobot<*>> withRobot(screenRobotClass: KClass<T>?): T {
            if (screenRobotClass == null) {
                throw IllegalArgumentException("instance class == null")
            }

            try {
                return screenRobotClass.primaryConstructor!!.call()
            } catch (iae: IllegalAccessException) {
                throw RuntimeException("IllegalAccessException", iae)
            } catch (ie: InstantiationException) {
                throw RuntimeException("InstantiationException", ie)
            }
        }
    }
}