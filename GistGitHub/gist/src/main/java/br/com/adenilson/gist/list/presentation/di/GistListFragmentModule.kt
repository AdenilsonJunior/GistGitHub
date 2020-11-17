package br.com.adenilson.gist.list.presentation.di

import androidx.lifecycle.ViewModel
import br.com.adenilson.base.presentation.viewmodel.ViewModelKey
import br.com.adenilson.core.domain.Executor
import br.com.adenilson.gist.common.data.repository.GistRepository
import br.com.adenilson.gist.common.data.repository.GistRepositoryImpl
import br.com.adenilson.gist.common.domain.mapper.GistEntityMapper
import br.com.adenilson.gist.common.domain.mapper.GistEntityMapperImpl
import br.com.adenilson.gist.common.domain.mapper.GistRemoteMapper
import br.com.adenilson.gist.common.domain.mapper.GistRemoteMapperImpl
import br.com.adenilson.gist.common.domain.usecase.FavoriteGistUseCase
import br.com.adenilson.gist.common.domain.usecase.FavoriteGistUseCaseImpl
import br.com.adenilson.gist.list.domain.datasource.GistListDataSource
import br.com.adenilson.gist.list.domain.usecase.GetGistListUseCase
import br.com.adenilson.gist.list.domain.usecase.GetGistListUseCaseImpl
import br.com.adenilson.gist.list.domain.usecase.UpdateIsFavoriteGistsUseCase
import br.com.adenilson.gist.list.domain.usecase.UpdateIsFavoriteGistsUseCaseImpl
import br.com.adenilson.gist.list.presentation.GistListViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class GistListFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(GistListViewModel::class)
    abstract fun bindGistListViewModel(viewModel: GistListViewModel): ViewModel

    @Binds
    abstract fun bindGetGistListUseCase(impl: GetGistListUseCaseImpl): GetGistListUseCase

    @Binds
    abstract fun bindFavoriteGistUseCase(impl: FavoriteGistUseCaseImpl): FavoriteGistUseCase

    @Binds
    abstract fun bindGisMapper(impl: GistRemoteMapperImpl): GistRemoteMapper

    @Binds
    abstract fun bindGistEntityMapper(impl: GistEntityMapperImpl): GistEntityMapper

    @Binds
    abstract fun bindCheckIsFavoriteGistsUseCaseImpl(impl: UpdateIsFavoriteGistsUseCaseImpl): UpdateIsFavoriteGistsUseCase

    @Binds
    abstract fun bindGistRepository(impl: GistRepositoryImpl): GistRepository

    companion object {
        @Provides
        fun providesGistDataSource(
            getGistListUseCase: GetGistListUseCase,
            executor: Executor
        ): GistListDataSource =
            GistListDataSource(executor, getGistListUseCase)
    }
}