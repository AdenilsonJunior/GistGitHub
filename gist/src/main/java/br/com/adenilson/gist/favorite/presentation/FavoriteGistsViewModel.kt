package br.com.adenilson.gist.favorite.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.adenilson.base.presentation.viewmodel.BaseViewModel
import br.com.adenilson.core.domain.Executor
import br.com.adenilson.gist.common.domain.usecase.FavoriteGistUseCase
import br.com.adenilson.gist.favorite.domain.usecase.GetFavoriteGistsUseCase
import br.com.adenilson.gist.common.domain.model.Gist
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

class FavoriteGistsViewModel @Inject constructor(
    private val executor: Executor,
    private val getFavoriteGistsUseCase: GetFavoriteGistsUseCase,
    private val favoriteGistsUseCase: FavoriteGistUseCase
) : BaseViewModel() {

    private val _loadingState: MutableLiveData<LoadingState> = MutableLiveData()
    val loadingState: LiveData<LoadingState> = _loadingState

    private val _favoriteGistsState: MutableLiveData<FavoriteGistsState> = MutableLiveData()
    val favoriteGistsState: LiveData<FavoriteGistsState> = _favoriteGistsState

    fun loadFavorites() {
        executor.execute(getFavoriteGistsUseCase, Unit)
            .doOnSubscribe { _loadingState.postValue(LoadingState.Start) }
            .doFinally { _loadingState.postValue(LoadingState.End) }
            .subscribeBy(
                onSuccess = { gists ->
                    if (gists.isNullOrEmpty()) {
                        _favoriteGistsState.postValue(FavoriteGistsState.Empty)
                    } else {
                        _favoriteGistsState.postValue(FavoriteGistsState.Loaded(gists))
                    }
                },
                onError = {
                    _favoriteGistsState.postValue(FavoriteGistsState.Error(it))
                }
            )
    }

    fun favoriteGist(gist: Gist) {
        executor.execute(favoriteGistsUseCase, gist)
            .subscribeBy(
                onComplete = {
                    if (gist.favorite) {
                        _favoriteGistsState.postValue(FavoriteGistsState.Favorite(gist))
                    } else {
                        _favoriteGistsState.postValue(FavoriteGistsState.UnFavorite(gist))
                    }
                },
                onError = {
                    _favoriteGistsState.postValue(FavoriteGistsState.Error(it))
                }
            )
    }

    sealed class FavoriteGistsState {
        data class Loaded(val gists: List<Gist>) : FavoriteGistsState()
        data class Error(val throwable: Throwable) : FavoriteGistsState()
        data class Favorite(val gist: Gist) : FavoriteGistsState()
        data class UnFavorite(val gist: Gist) : FavoriteGistsState()
        object Empty : FavoriteGistsState()
    }

    sealed class LoadingState {
        object Start : LoadingState()
        object End : LoadingState()
    }
}