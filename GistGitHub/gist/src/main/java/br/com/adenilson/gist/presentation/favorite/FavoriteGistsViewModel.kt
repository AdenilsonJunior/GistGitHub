package br.com.adenilson.gist.presentation.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.adenilson.base.presentation.BaseViewModel
import br.com.adenilson.core.domain.Executor
import br.com.adenilson.gist.domain.interactor.GetFavoriteGistsInteractor
import br.com.adenilson.gist.presentation.model.Gist
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

class FavoriteGistsViewModel @Inject constructor(
    private val executor: Executor,
    private val interactor: GetFavoriteGistsInteractor
) : BaseViewModel() {


    private val _loadingState: MutableLiveData<LoadingState> = MutableLiveData()
    val loadingState: LiveData<LoadingState> = _loadingState

    private val _favoriteGistsState: MutableLiveData<FavoriteGistsState> = MutableLiveData()
    val favoriteGistsState: LiveData<FavoriteGistsState> = _favoriteGistsState

    fun loadFavorites() {
        executor.execute(interactor, Unit)
            .doOnSubscribe {
                _loadingState.postValue(LoadingState.Start)
            }
            .doFinally { _loadingState.postValue(LoadingState.End) }
            .subscribeBy(
                onSuccess = {
                    _favoriteGistsState.postValue(FavoriteGistsState.onLoaded(it))
                },
                onError = {
                    _favoriteGistsState.postValue(FavoriteGistsState.onError(it))
                }
            )
    }

    sealed class FavoriteGistsState {
        data class onLoaded(val gists: List<Gist>) : FavoriteGistsState()
        data class onError(val throwable: Throwable) : FavoriteGistsState()
    }

    sealed class LoadingState {
        object Start : LoadingState()
        object End : LoadingState()
    }
}