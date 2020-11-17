package br.com.adenilson.gistgithub.android.features.list

import androidx.lifecycle.ViewModel
import br.com.adenilson.base.viewmodel.ViewModelKey
import br.com.adenilson.core.domain.Executor
import br.com.adenilson.gist.domain.datasource.GistListDataSource
import br.com.adenilson.gist.domain.interactor.UpdateIsFavoriteGistsInteractor
import br.com.adenilson.gist.domain.interactor.UpdateIsFavoriteGistsInteractorImpl
import br.com.adenilson.gist.domain.interactor.FavoriteGistInteractor
import br.com.adenilson.gist.domain.interactor.FavoriteGistInteractorImpl
import br.com.adenilson.gist.domain.interactor.GetGistListInteractor
import br.com.adenilson.gist.domain.interactor.GetGistListInteractorImpl
import br.com.adenilson.gist.domain.mapper.GistMapper
import br.com.adenilson.gist.domain.mapper.GistMapperImpl
import br.com.adenilson.gist.domain.mapper.GistModelMapper
import br.com.adenilson.gist.domain.mapper.GistModelMapperImpl
import br.com.adenilson.gist.presentation.list.GistListViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class GistListFragmentModuleMock {

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

    @Binds
    abstract fun bindCheckIsFavoriteGistsInteractorImpl(impl: UpdateIsFavoriteGistsInteractorImpl): UpdateIsFavoriteGistsInteractor

    companion object {
        @Provides
        fun providesGistDataSource(
            getGistListInteractor: GetGistListInteractor,
            updateIsFavoriteGistsInteractor: UpdateIsFavoriteGistsInteractor,
            executor: Executor
        ): GistListDataSource =
            GistListDataSource(executor, getGistListInteractor, updateIsFavoriteGistsInteractor)
    }
}