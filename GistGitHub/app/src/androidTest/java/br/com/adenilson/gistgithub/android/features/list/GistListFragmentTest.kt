package br.com.adenilson.gistgithub.android.features.list

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.LargeTest
import br.com.adenilson.gist.presentation.list.GistListFragment
import br.com.adenilson.gistgithub.R
import br.com.adenilson.gistgithub.android.helper.useFragmentInContainer
import org.junit.Test

@LargeTest
class GistListFragmentTest {

    @Test
    fun should() {
        useFragmentInContainer<GistListFragment>(factory = Factory()) {
            Espresso.onView(ViewMatchers.withId(R.id.cardSearch)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }
    }
}

class Factory : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return TestMarketplaceGenericCoverTopFragment()
    }
}

class TestMarketplaceGenericCoverTopFragment : GistListFragment() {
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
}