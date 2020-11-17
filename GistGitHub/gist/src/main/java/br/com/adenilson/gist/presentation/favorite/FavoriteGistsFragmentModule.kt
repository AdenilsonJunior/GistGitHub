package br.com.adenilson.gist.presentation.favorite

import androidx.lifecycle.ViewModel
import br.com.adenilson.base.viewmodel.ViewModelKey
import br.com.adenilson.gist.data.GistRepository
import br.com.adenilson.gist.data.GistRepositoryImpl
import br.com.adenilson.gist.domain.interactor.FavoriteGistInteractor
import br.com.adenilson.gist.domain.interactor.FavoriteGistInteractorImpl
import br.com.adenilson.gist.domain.interactor.GetFavoriteGistsInteractor
import br.com.adenilson.gist.domain.interactor.GetFavoriteGistsInteractorImpl
import br.com.adenilson.gist.domain.mapper.GistEntityMapper
import br.com.adenilson.gist.domain.mapper.GistEntityMapperImpl
import br.com.adenilson.gist.domain.mapper.GistLocalMapper
import br.com.adenilson.gist.domain.mapper.GistLocalMapperImpl
import br.com.adenilson.gist.domain.mapper.GistRemoteMapper
import br.com.adenilson.gist.domain.mapper.GistRemoteMapperImpl
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