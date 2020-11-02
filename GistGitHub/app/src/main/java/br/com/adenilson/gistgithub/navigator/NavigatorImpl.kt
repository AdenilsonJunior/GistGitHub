package br.com.adenilson.gistgithub.navigator

import android.app.Activity
import android.content.Intent
import br.com.adenilson.base.navigator.Navigator
import br.com.adenilson.gist.list.GistListActivity
import javax.inject.Inject

class NavigatorImpl @Inject constructor() : Navigator {

    override fun navigateToGistListActivity(activity: Activity) {
        activity.run {
            Intent(activity, GistListActivity::class.java).also { intent ->
                startActivity(intent)
            }
        }
    }
}