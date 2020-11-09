package br.com.adenilson.gistgithub.android.features.details

import androidx.test.espresso.Espresso
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import br.com.adenilson.gist.presentation.details.adapter.FileViewHolder
import br.com.adenilson.gistgithub.R
import br.com.adenilson.gistgithub.android.actions.CustomViewActions
import br.com.adenilson.gistgithub.android.robot.ScreenRobot

class GistDetailsScreenRobot : ScreenRobot<GistDetailsScreenRobot>() {
    fun checkDetailsListIsDisplayed(): GistDetailsScreenRobot {
        return checkIsDisplayed(R.id.recyclerViewDetails)
    }

    fun checkHasHeaderItem(positionInLayout: Int): GistDetailsScreenRobot {
        return checkRecyclerViewItemIsDisplayed(
            R.id.recyclerViewDetails,
            positionInLayout,
            R.id.rootHeader
        )
    }

    fun checkHasFileHeaderItem(positionInLayout: Int): GistDetailsScreenRobot {
        return checkRecyclerViewItemIsDisplayed(
            R.id.recyclerViewDetails,
            positionInLayout,
            R.id.rootFileHeader
        )
    }

    fun checkHasFileItem(positionInLayout: Int): GistDetailsScreenRobot {
        return checkRecyclerViewItemIsDisplayed(
            R.id.recyclerViewDetails,
            positionInLayout,
            R.id.rootFile
        )
    }

    fun checkHasUpdateDateItem(positionInLayout: Int): GistDetailsScreenRobot {
        return checkRecyclerViewItemIsDisplayed(
            R.id.recyclerViewDetails,
            positionInLayout,
            R.id.textViewLastUpdate
        )
    }

    fun clickInFile(positionInLayout: Int) {
        Espresso.onView(ViewMatchers.withId(R.id.recyclerViewDetails)).perform(
            RecyclerViewActions.actionOnItemAtPosition<FileViewHolder>(
                positionInLayout,
                CustomViewActions.clickChildViewWithId(R.id.rootFile)
            )
        )
    }
}