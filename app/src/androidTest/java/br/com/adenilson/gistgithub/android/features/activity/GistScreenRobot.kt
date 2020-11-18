package br.com.adenilson.gistgithub.android.features.activity

import br.com.adenilson.gistgithub.R
import br.com.adenilson.gistgithub.android.robot.ScreenRobot

class GistScreenRobot : ScreenRobot<GistScreenRobot>() {
    fun clickFavoriteMenu(): GistScreenRobot {
        return clickOkOnView(R.id.menuFavorites)
    }
}