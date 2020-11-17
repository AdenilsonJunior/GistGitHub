package br.com.adenilson.gistgithub.daggermock

import br.com.adenilson.gist.GistActivity
import br.com.adenilson.gistgithub.android.features.details.GistDetailsFragmentModuleMock
import br.com.adenilson.gistgithub.android.features.details.TestGistDetailsFragment
import br.com.adenilson.gistgithub.android.features.list.GistListFragmentModuleMock
import br.com.adenilson.gistgithub.android.features.list.TestGistListFragment
import br.com.adenilson.splash.SplashActivity
import br.com.adenilson.splash.SplashActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AndroidBindingModuleMock {

    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    abstract fun buildSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [GistFragmentsModuleMock::class])
    abstract fun buildGistListActivity(): GistActivity

    @ContributesAndroidInjector(modules = [GistListFragmentModuleMock::class])
    abstract fun buildGistListFragment(): TestGistListFragment

    @ContributesAndroidInjector(modules = [GistDetailsFragmentModuleMock::class])
    abstract fun buildGistDetailsFragment(): TestGistDetailsFragment
}