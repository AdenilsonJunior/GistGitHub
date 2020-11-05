package br.com.adenilson.gist.list.presentation.list

import androidx.lifecycle.ViewModel
import br.com.adenilson.base.di.ViewModelKey
import br.com.adenilson.base.domain.Executor
import br.com.adenilson.gist.list.domain.interactor.GetGistListInteractor
import br.com.adenilson.gist.list.domain.interactor.GetGistListInteractorImpl
import br.com.adenilson.gist.list.domain.mapper.GistListMapper
import br.com.adenilson.gist.list.domain.mapper.GistListMapperImpl
import br.com.adenilson.gist.list.presentation.list.paging.GistListDataSource
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
    abstract fun bindGistListMapper(impl: GistListMapperImpl): GistListMapper

    companion object {
        @Provides
        fun providesGistDataSource(
            interactor: GetGistListInteractor,
            executor: Executor
        ): GistListDataSource =
            GistListDataSource(executor, interactor)
    }
}