package br.com.adenilson.gistgithub.application.di

import br.com.adenilson.gist.commons.presentation.GistActivity
import br.com.adenilson.gist.commons.presentation.di.GistFragmentsModule
import br.com.adenilson.splash.presentation.SplashActivity
import br.com.adenilson.splash.di.SplashActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AndroidBindingModule {

    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    abstract fun buildSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [GistFragmentsModule::class])
    abstract fun buildGistListActivity(): GistActivity
}