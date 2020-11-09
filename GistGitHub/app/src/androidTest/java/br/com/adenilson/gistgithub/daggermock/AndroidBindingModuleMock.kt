package br.com.adenilson.gistgithub.daggermock

import br.com.adenilson.gist.GistActivity
import br.com.adenilson.gist.GistFragmentsModule
import br.com.adenilson.gistgithub.splash.SplashActivity
import br.com.adenilson.gistgithub.splash.SplashActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AndroidBindingModuleMock {
    
    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    abstract fun buildSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [GistFragmentsModule::class])
    abstract fun buildGistListActivity(): GistActivity
}