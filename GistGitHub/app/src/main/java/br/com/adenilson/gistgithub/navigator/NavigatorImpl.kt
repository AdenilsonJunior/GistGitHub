package br.com.adenilson.gistgithub.navigator

import android.content.Context
import android.content.Intent
import android.net.Uri
import br.com.adenilson.base.androidextensions.showLongToast
import br.com.adenilson.base.navigator.Navigator
import br.com.adenilson.gist.GistActivity
import br.com.adenilson.gistgithub.R
import javax.inject.Inject

class NavigatorImpl @Inject constructor() : Navigator {

    override fun navigateToGistListActivity(context: Context) {
        context.run {
            Intent(context, GistActivity::class.java).also { intent ->
                startActivity(intent)
            }
        }
    }

    override fun navigateToBrowser(context: Context, url: String) {
        context.run {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            if (intent.resolveActivity(context.packageManager) != null) {
                startActivity(intent)
            } else {
                showLongToast(getString(R.string.gist_url_error))
            }
        }
    }
}