package br.com.adenilson.gist.presentation.list

import androidx.lifecycle.ViewModel
import br.com.adenilson.base.di.ViewModelKey
import br.com.adenilson.core.domain.Executor
import br.com.adenilson.gist.domain.datasource.GistListDataSource
import br.com.adenilson.gist.domain.interactor.FavoriteGistInteractor
import br.com.adenilson.gist.domain.interactor.FavoriteGistInteractorImpl
import br.com.adenilson.gist.domain.interactor.GetGistListInteractor
import br.com.adenilson.gist.domain.interactor.GetGistListInteractorImpl
import br.com.adenilson.gist.domain.mapper.GistMapper
import br.com.adenilson.gist.domain.mapper.GistMapperImpl
import br.com.adenilson.gist.domain.mapper.GistModelMapper
import br.com.adenilson.gist.domain.mapper.GistModelMapperImpl
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
    abstract fun bindGetGistListInteractor(impl: GetGistListInteractorImpl): GetGistListInteractor

    @Binds
    abstract fun bindFavoriteGistInteractor(impl: FavoriteGistInteractorImpl): FavoriteGistInteractor

    @Binds
    abstract fun bindGisMapper(impl: GistMapperImpl): GistMapper

    @Binds
    abstract fun bindGistModelMapper(impl: GistModelMapperImpl): GistModelMapper

    companion object {
        @Provides
        fun providesGistDataSource(
            interactor: GetGistListInteractor,
            executor: Executor
        ): GistListDataSource =
            GistListDataSource(executor, interactor)
    }
}