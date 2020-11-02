package br.com.adenilson.gistgithub.application.di

import br.com.adenilson.gistgithub.application.GistGitHubApplication
import br.com.adenilson.gistgithub.di.module.AndroidBindingModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        GistGitHubApplicationModule::class,
        AndroidBindingModule::class
    ]
)
interface GistGitHubApplicationComponent : AndroidInjector<GistGitHubApplication> {

    @Component.Factory
    abstract class Builder : AndroidInjector.Factory<GistGitHubApplication>
}