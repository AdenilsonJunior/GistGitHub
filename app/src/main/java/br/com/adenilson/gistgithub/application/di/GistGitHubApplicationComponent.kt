package br.com.adenilson.gistgithub.application.di

import br.com.adenilson.gistgithub.application.GistGitHubApplication
import br.com.adenilson.gistgithub.di.AndroidBindingApplication
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        GistGitHubApplicationModule::class,
        AndroidBindingApplication::class
    ]
)
interface GistGitHubApplicationComponent : AndroidInjector<GistGitHubApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Factory<GistGitHubApplication>
}