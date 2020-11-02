package br.com.adenilson.gistgithub.application.di

import br.com.adenilson.gistgithub.application.GistGitHubApplication
import br.com.adenilson.gistgithub.di.AndroidBindingApplication
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        GistGitHubApplicationModule::class,
        AndroidBindingApplication::class
    ]
)
interface GistGitHubApplicationComponent : AndroidInjector<GistGitHubApplication> {

    @Component.Factory
    abstract class Builder : AndroidInjector.Factory<GistGitHubApplication>
}