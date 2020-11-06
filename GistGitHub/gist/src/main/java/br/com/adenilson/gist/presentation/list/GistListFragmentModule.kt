package br.com.adenilson.gist.presentation.list

import androidx.lifecycle.ViewModel
import br.com.adenilson.base.di.ViewModelKey
import br.com.adenilson.base.domain.Executor
import br.com.adenilson.gist.domain.datasouruce.GistListDataSource
import br.com.adenilson.gist.domain.interactor.GetGistListInteractor
import br.com.adenilson.gist.domain.interactor.GetGistListInteractorImpl
import br.com.adenilson.gist.domain.mapper.GistListMapper
import br.com.adenilson.gist.domain.mapper.GistListMapperImpl
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