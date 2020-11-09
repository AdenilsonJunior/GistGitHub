package br.com.adenilson.gistgithub.android.features.activity

import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.test.filters.LargeTest
import br.com.adenilson.gist.GistActivity
import br.com.adenilson.gistgithub.R
import br.com.adenilson.gistgithub.android.helper.useActivity
import br.com.adenilson.gistgithub.android.robot.ScreenRobot.Companion.withRobot
import junit.framework.TestCase.assertEquals
import org.junit.Test

@LargeTest
class GistActivityTest {

    private val screenRobot: GistScreenRobot
        get() = withRobot(GistScreenRobot::class)

    @Test
    fun should_ClickOn_FavoriteMenuItem_NavigatesTo_List() {
        var navController: NavController? = null

        useActivity<GistActivity> {
            it.onActivity { activity ->
                navController = activity.findNavController(R.id.navFragmentHost)
            }
            screenRobot.clickFavoriteMenu()
            assertEquals(R.id.fragmentFavoriteGists, navController?.currentDestination?.id)
        }
    }
}