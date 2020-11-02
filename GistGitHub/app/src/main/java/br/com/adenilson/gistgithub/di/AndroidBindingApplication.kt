package br.com.adenilson.gistgithub.di

import br.com.adenilson.gist.list.GistListActivity
import br.com.adenilson.gistgithub.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AndroidBindingApplication {

    @ContributesAndroidInjector
    abstract fun buildSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun buildGistListActivity(): GistListActivity
}