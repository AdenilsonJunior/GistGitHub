package br.com.adenilson.gistgithub.daggermock

import br.com.adenilson.gist.favorite.presentation.FavoriteGistsFragment
import br.com.adenilson.gist.favorite.presentation.di.FavoriteGistsFragmentModule
import br.com.adenilson.gist.list.presentation.GistListFragment
import br.com.adenilson.gist.list.presentation.di.GistListFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class GistFragmentsModuleMock {

    @ContributesAndroidInjector(modules = [FavoriteGistsFragmentModule::class])
    abstract fun buildFavoriteGistsFragment(): FavoriteGistsFragment

    @ContributesAndroidInjector(modules = [GistListFragmentModule::class])
    abstract fun buildGistListFragment(): GistListFragment
}