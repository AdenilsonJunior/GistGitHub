package br.com.adenilson.gist.common.presentation.di

import br.com.adenilson.gist.details.presentation.GistDetailsFragment
import br.com.adenilson.gist.details.presentation.di.GistDetailsFragmentModule
import br.com.adenilson.gist.favorite.presentation.FavoriteGistsFragment
import br.com.adenilson.gist.favorite.presentation.di.FavoriteGistsFragmentModule
import br.com.adenilson.gist.list.presentation.GistListFragment
import br.com.adenilson.gist.list.presentation.di.GistListFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class GistFragmentsModule {

    @ContributesAndroidInjector(modules = [GistListFragmentModule::class])
    abstract fun buildGistListFragment(): GistListFragment

    @ContributesAndroidInjector(modules = [GistDetailsFragmentModule::class])
    abstract fun buildGistDetailsFragment(): GistDetailsFragment

    @ContributesAndroidInjector(modules = [FavoriteGistsFragmentModule::class])
    abstract fun buildFavoriteGistsFragment(): FavoriteGistsFragment
}