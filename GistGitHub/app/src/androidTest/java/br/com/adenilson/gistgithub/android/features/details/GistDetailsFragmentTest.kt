package br.com.adenilson.gistgithub.android.features.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.filters.LargeTest
import br.com.adenilson.gist.presentation.details.GistDetailsFragment
import br.com.adenilson.gist.presentation.model.File
import br.com.adenilson.gist.presentation.model.Gist
import br.com.adenilson.gist.presentation.model.Owner
import br.com.adenilson.gistgithub.android.helper.useFragmentInContainer
import br.com.adenilson.gistgithub.android.robot.ScreenRobot.Companion.withRobot
import org.hamcrest.Matchers
import org.junit.Test
import java.util.Date

@LargeTest
class GistDetailsFragmentTest {

    private val screenRobot: GistDetailsScreenRobot
        get() = withRobot(GistDetailsScreenRobot::class)

    private val args: Bundle = Bundle().apply {
        putParcelable(
            "gist", Gist(
                id = 1,
                owner = Owner(
                    id = 1,
                    avatarUrl = "https://avatars1.githubusercontent.com/u/24987474?v=4",
                    name = "Test"
                ),
                webId = "webId",
                description = "description",
                lastUpdate = Date(),
                files = listOf(
                    File(
                        id = 1,
                        filename = "filename",
                        type = "type",
                        size = 1,
                        language = "language",
                        rawUrl = "https://gist.githubusercontent.com/pandidan/d6390841b4ecb46a7a6fc61fb2fe5197/raw/19db1443ef6168a35cd37448ecae3a48dbb1430a/jenkins_list_groups.groovy"
                    ),
                    File(
                        id = 1,
                        filename = "filename",
                        type = "type",
                        size = 1,
                        language = "language",
                        rawUrl = "rawUrl"
                    )
                )
            )
        )
    }

    @Test
    fun should_Check_Details() {
        useFragmentInContainer<GistDetailsFragment>(factory = Factory(), args = args) {
            it.moveToState(Lifecycle.State.RESUMED)

            screenRobot
                .checkDetailsListIsDisplayed()
                .checkHasHeaderItem(0)
                .checkHasFileHeaderItem(1)
                .checkHasFileItem(2)
                .checkHasFileItem(3)
                .checkHasUpdateDateItem(4)
        }
    }

    @Test
    fun should_ClickOn_File_NavigatesTo_ActionView() {
        Intents.init()
        useFragmentInContainer<GistDetailsFragment>(factory = Factory(), args = args) {
            it.moveToState(Lifecycle.State.RESUMED)
            screenRobot
                .clickInFile(2)
        }
        Intents.intended(
            Matchers.allOf(
                IntentMatchers.hasAction(Intent.ACTION_VIEW),
                IntentMatchers.hasData((Uri.parse("https://gist.githubusercontent.com/pandidan/d6390841b4ecb46a7a6fc61fb2fe5197/raw/19db1443ef6168a35cd37448ecae3a48dbb1430a/jenkins_list_groups.groovy")))
            )
        )
        Intents.release()
    }
}

class Factory : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return TestGistDetailsFragment()
    }
}

class TestGistDetailsFragment : GistDetailsFragment()