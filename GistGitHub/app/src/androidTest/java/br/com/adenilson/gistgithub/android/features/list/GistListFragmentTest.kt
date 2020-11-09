package br.com.adenilson.gistgithub.android.features.list

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.test.filters.LargeTest
import br.com.adenilson.gist.presentation.list.GistListFragment
import br.com.adenilson.gistgithub.android.helper.useFragmentInContainer
import br.com.adenilson.gistgithub.android.robot.ScreenRobot.Companion.withRobot
import org.junit.Test

@LargeTest
class GistListFragmentTest {

    private val screenRobot: GistListScreenRobot
        get() = withRobot(GistListScreenRobot::class)

    @Test
    fun should_Search_IsDisplayed() {
        useFragmentInContainer<GistListFragment>(factory = Factory()) {
            screenRobot.checkSearchIsDisplayed()
        }
    }
}

class Factory : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return TestGistListFragment()
    }
}

class TestGistListFragment : GistListFragment()