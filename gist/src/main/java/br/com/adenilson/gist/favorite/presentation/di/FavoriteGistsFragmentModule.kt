package br.com.adenilson.gist.favorite.presentation.di

import androidx.lifecycle.ViewModel
import br.com.adenilson.base.presentation.viewmodel.ViewModelKey
import br.com.adenilson.gist.common.data.repository.GistRepository
import br.com.adenilson.gist.common.data.repository.GistRepositoryImpl
import br.com.adenilson.gist.common.domain.usecase.FavoriteGistUseCase
import br.com.adenilson.gist.common.domain.usecase.FavoriteGistUseCaseImpl
import br.com.adenilson.gist.favorite.domain.usecase.GetFavoriteGistsUseCase
import br.com.adenilson.gist.favorite.domain.usecase.GetFavoriteGistsUseCaseImpl
import br.com.adenilson.gist.common.domain.mapper.GistEntityMapper
import br.com.adenilson.gist.common.domain.mapper.GistEntityMapperImpl
import br.com.adenilson.gist.favorite.domain.mapper.GistLocalMapper
import br.com.adenilson.gist.favorite.domain.mapper.GistLocalMapperImpl
import br.com.adenilson.gist.common.domain.mapper.GistRemoteMapper
import br.com.adenilson.gist.common.domain.mapper.GistRemoteMapperImpl
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
    abstract fun bindGetFavoriteGistsUseCase(impl: GetFavoriteGistsUseCaseImpl): GetFavoriteGistsUseCase

    @Binds
    abstract fun bindFavoriteGistsUseCase(impl: FavoriteGistUseCaseImpl): FavoriteGistUseCase

    @Binds
    abstract fun bindGistMapper(impl: GistRemoteMapperImpl): GistRemoteMapper

    @Binds
    abstract fun bindGistModelMapper(impl: GistLocalMapperImpl): GistLocalMapper

    @Binds
    abstract fun bindGistEntityMapper(impl: GistEntityMapperImpl): GistEntityMapper

    @Binds
    abstract fun bindGistRepository(impl: GistRepositoryImpl): GistRepository
}