package br.com.adenilson.gistgithub.android.features.list

import br.com.adenilson.gistgithub.R
import br.com.adenilson.gistgithub.android.robot.ScreenRobot

class GistListScreenRobot : ScreenRobot<GistListScreenRobot>() {

    fun checkSearchIsDisplayed(): GistListScreenRobot {
        return checkIsDisplayed(R.id.cardSearch)
    }
}