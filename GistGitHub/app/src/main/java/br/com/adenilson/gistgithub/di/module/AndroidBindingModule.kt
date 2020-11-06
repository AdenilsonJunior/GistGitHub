package br.com.adenilson.gistgithub.di.module

import br.com.adenilson.gist.GistActivity
import br.com.adenilson.gist.GistFragmentsModule
import br.com.adenilson.gistgithub.splash.SplashActivity
import br.com.adenilson.gistgithub.splash.SplashActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AndroidBindingModule {

    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    abstract fun buildSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [GistFragmentsModule::class])
    abstract fun buildGistListActivity(): GistActivity
}