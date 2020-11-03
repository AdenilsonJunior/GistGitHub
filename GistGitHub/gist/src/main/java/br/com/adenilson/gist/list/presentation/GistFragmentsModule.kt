package br.com.adenilson.gist.list.presentation

import br.com.adenilson.gist.list.presentation.list.GistListFragment
import br.com.adenilson.gist.list.presentation.list.GistListFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class GistFragmentsModule {

    @ContributesAndroidInjector(modules = [GistListFragmentModule::class])
    abstract fun buildGistListFragment(): GistListFragment
}