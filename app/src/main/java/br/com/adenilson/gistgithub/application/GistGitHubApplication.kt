package br.com.adenilson.gistgithub.application

import br.com.adenilson.gistgithub.application.di.DaggerGistGitHubApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

open class GistGitHubApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out GistGitHubApplication> {
        return DaggerGistGitHubApplicationComponent.factory().create(this)
    }
}