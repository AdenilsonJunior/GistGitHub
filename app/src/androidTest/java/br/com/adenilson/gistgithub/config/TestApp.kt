package br.com.adenilson.gistgithub.config

import br.com.adenilson.gistgithub.application.GistGitHubApplication
import br.com.adenilson.gistgithub.daggermock.DaggerTestComponent
import dagger.android.AndroidInjector

class TestApp : GistGitHubApplication() {

    override fun applicationInjector(): AndroidInjector<out GistGitHubApplication> {
        return DaggerTestComponent.factory().create(this)
    }
}