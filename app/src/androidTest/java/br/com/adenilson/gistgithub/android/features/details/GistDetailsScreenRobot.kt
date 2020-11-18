package br.com.adenilson.gistgithub.android.features.details

import br.com.adenilson.gist.details.presentation.adapter.FileViewHolder
import br.com.adenilson.gist.details.presentation.adapter.HtmlUrlViewHolder
import br.com.adenilson.gistgithub.R
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

    fun checkHasHtmlUrlItem(positionInLayout: Int): GistDetailsScreenRobot {
        return checkRecyclerViewItemIsDisplayed(
            R.id.recyclerViewDetails,
            positionInLayout,
            R.id.textViewHtmlUrl
        )
    }

    fun clickInFile(positionInLayout: Int): GistDetailsScreenRobot {
        return clickInRecyclerView<FileViewHolder>(
            recyclerViewId = R.id.recyclerViewDetails,
            positionInLayout = positionInLayout,
            rootIdItem = R.id.rootFile
        )
    }

    fun clickInHtmlUrl(positionInLayout: Int): GistDetailsScreenRobot {
        return clickInRecyclerView<HtmlUrlViewHolder>(
            recyclerViewId = R.id.recyclerViewDetails,
            positionInLayout = positionInLayout,
            rootIdItem = R.id.textViewHtmlUrl
        )
    }
}