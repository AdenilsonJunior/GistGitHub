package br.com.adenilson.gist

import br.com.adenilson.gist.presentation.details.GistDetailsFragment
import br.com.adenilson.gist.presentation.details.GistDetailsFragmentModule
import br.com.adenilson.gist.presentation.favorite.FavoriteGistsFragment
import br.com.adenilson.gist.presentation.favorite.FavoriteGistsFragmentModule
import br.com.adenilson.gist.presentation.list.GistListFragment
import br.com.adenilson.gist.presentation.list.GistListFragmentModule
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