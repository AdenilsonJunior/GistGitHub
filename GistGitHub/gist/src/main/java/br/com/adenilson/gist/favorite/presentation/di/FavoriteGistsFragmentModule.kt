package br.com.adenilson.gist.favorite.presentation.di

import androidx.lifecycle.ViewModel
import br.com.adenilson.base.presentation.viewmodel.ViewModelKey
import br.com.adenilson.gist.commons.data.repository.GistRepository
import br.com.adenilson.gist.commons.data.repository.GistRepositoryImpl
import br.com.adenilson.gist.commons.domain.interactor.FavoriteGistInteractor
import br.com.adenilson.gist.commons.domain.interactor.FavoriteGistInteractorImpl
import br.com.adenilson.gist.favorite.domain.interactor.GetFavoriteGistsInteractor
import br.com.adenilson.gist.favorite.domain.interactor.GetFavoriteGistsInteractorImpl
import br.com.adenilson.gist.commons.domain.mapper.GistEntityMapper
import br.com.adenilson.gist.commons.domain.mapper.GistEntityMapperImpl
import br.com.adenilson.gist.favorite.domain.mapper.GistLocalMapper
import br.com.adenilson.gist.favorite.domain.mapper.GistLocalMapperImpl
import br.com.adenilson.gist.commons.domain.mapper.GistRemoteMapper
import br.com.adenilson.gist.commons.domain.mapper.GistRemoteMapperImpl
import br.com.adenilson.gist.favorite.presentation.FavoriteGistsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FavoriteGistsFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteGistsViewModel::class)
    abstract fun bindFavoriteGistsViewModel(viewModel: FavoriteGistsViewModel): ViewModel

    @Binds
    abstract fun bindGetFavoriteGistsInteractor(impl: GetFavoriteGistsInteractorImpl): GetFavoriteGistsInteractor

    @Binds
    abstract fun bindFavoriteGistsInteractor(impl: FavoriteGistInteractorImpl): FavoriteGistInteractor

    @Binds
    abstract fun bindGistMapper(impl: GistRemoteMapperImpl): GistRemoteMapper

    @Binds
    abstract fun bindGistModelMapper(impl: GistLocalMapperImpl): GistLocalMapper

    @Binds
    abstract fun bindGistEntityMapper(impl: GistEntityMapperImpl): GistEntityMapper

    @Binds
    abstract fun bindGistRepository(impl: GistRepositoryImpl): GistRepository
}