package br.com.adenilson.gist.list.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.adenilson.base.domain.Executor
import br.com.adenilson.base.presentation.BaseViewModel
import br.com.adenilson.gist.list.domain.interactor.GetGistListInteractor
import br.com.adenilson.gist.list.domain.model.Gist
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

class GistListViewModel @Inject constructor(
    private val getGistListInteractor: GetGistListInteractor,
    private val executor: Executor
) : BaseViewModel() {

    private val _loadingState: MutableLiveData<LoadingState> = MutableLiveData()
    val loadingState: LiveData<LoadingState> = _loadingState

    private val _gistListState: MutableLiveData<GistListState> = MutableLiveData()
    val gistListState: LiveData<GistListState> = _gistListState

    fun loadGist() {
        executor.execute(getGistListInteractor, Unit)
            .doOnSubscribe { _loadingState.postValue(LoadingState.Start) }
            .doFinally { _loadingState.postValue(LoadingState.Finish) }
            .subscribeBy(
                onSuccess = {
                    _gistListState.postValue(
                        GistListState.Loaded(
                            it
                        )
                    )
                },
                onError = {
                    _gistListState.postValue(
                        GistListState.Error(
                            it
                        )
                    )
                }
            )
    }

    fun clearStates() {
        _gistListState.value = null
        _loadingState.value = null
    }

    sealed class GistListState {
        data class Loaded(val model: List<Gist>) : GistListState()
        data class Error(val throwable: Throwable) : GistListState()
    }

    sealed class LoadingState {
        object Start : LoadingState()
        object Finish : LoadingState()
    }
}