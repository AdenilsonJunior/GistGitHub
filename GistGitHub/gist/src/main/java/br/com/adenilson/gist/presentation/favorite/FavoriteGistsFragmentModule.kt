package br.com.adenilson.gist.presentation.favorite

import androidx.lifecycle.ViewModel
import br.com.adenilson.base.di.ViewModelKey
import br.com.adenilson.core.domain.Executor
import br.com.adenilson.gist.domain.interactor.FavoriteGistInteractor
import br.com.adenilson.gist.domain.interactor.FavoriteGistInteractorImpl
import br.com.adenilson.gist.domain.interactor.GetFavoriteGistsInteractor
import br.com.adenilson.gist.domain.interactor.GetFavoriteGistsInteractorImpl
import br.com.adenilson.gist.domain.mapper.GistMapper
import br.com.adenilson.gist.domain.mapper.GistMapperImpl
import br.com.adenilson.gist.domain.mapper.GistModelMapper
import br.com.adenilson.gist.domain.mapper.GistModelMapperImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
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
    abstract fun bindGistMapper(impl: GistMapperImpl): GistMapper

    @Binds
    abstract fun bindGistModelMapper(impl: GistModelMapperImpl): GistModelMapper
}