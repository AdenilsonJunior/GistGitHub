package br.com.adenilson.gistgithub.application.di

import br.com.adenilson.base.navigator.Navigator
import br.com.adenilson.gistgithub.navigator.NavigatorImpl
import dagger.Binds
import dagger.Module

@Module
abstract class GistGitHubApplicationModule {

    @Binds
    abstract fun bindNavigator(impl: NavigatorImpl): Navigator
}