package br.com.adenilson.base.navigator

import android.content.Context

interface Navigator {
    fun navigateToGistListActivity(context: Context)
    fun navigateToBrowser(context: Context, url: String)
}