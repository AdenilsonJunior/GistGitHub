package br.com.adenilson.gistgithub.daggermock

import br.com.adenilson.gist.presentation.favorite.FavoriteGistsFragment
import br.com.adenilson.gist.presentation.favorite.FavoriteGistsFragmentModule
import br.com.adenilson.gist.presentation.list.GistListFragment
import br.com.adenilson.gist.presentation.list.GistListFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class GistFragmentsModuleMock {

    @ContributesAndroidInjector(modules = [FavoriteGistsFragmentModule::class])
    abstract fun buildFavoriteGistsFragment(): FavoriteGistsFragment

    @ContributesAndroidInjector(modules = [GistListFragmentModule::class])
    abstract fun buildGistListFragment(): GistListFragment
}