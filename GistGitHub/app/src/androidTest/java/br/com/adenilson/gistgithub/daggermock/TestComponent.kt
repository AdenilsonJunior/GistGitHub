package br.com.adenilson.gistgithub.daggermock

import br.com.adenilson.gistgithub.application.GistGitHubApplication
import br.com.adenilson.gistgithub.application.di.GistGitHubApplicationModule
import br.com.adenilson.infrastructure.di.InfrastructureModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidBindingModuleMock::class,
        GistGitHubApplicationModule::class,
        InfrastructureModule::class
    ]
)
interface TestComponent : AndroidInjector<GistGitHubApplication> {
    @Component.Factory
    abstract class Builder : AndroidInjector.Factory<GistGitHubApplication>
}