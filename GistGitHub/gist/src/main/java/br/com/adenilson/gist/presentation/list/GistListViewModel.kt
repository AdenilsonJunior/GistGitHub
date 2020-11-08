package br.com.adenilson.gist.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import br.com.adenilson.core.domain.Executor
import br.com.adenilson.base.presentation.BaseViewModel
import br.com.adenilson.gist.domain.datasource.GistListDataSource
import br.com.adenilson.gist.domain.interactor.FavoriteGistInteractor
import br.com.adenilson.gist.presentation.model.Gist
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

class GistListViewModel @Inject constructor(
    private val pagingSource: GistListDataSource,
    private val executor: Executor,
    private val favoriteGistInteractor: FavoriteGistInteractor
) : BaseViewModel() {

    companion object {
        private const val PAGE_SIZE = 30
        private const val PRE_FETCH_DISTANCE = 10
        private const val ENABLE_PLACEHOLDERS = false
        private const val INITIAL_KEY_PAGE = 0
    }

    private var _favoriteGistState: MutableLiveData<FavoriteGistState> = MutableLiveData()
    val favoriteGistState: LiveData<FavoriteGistState> = _favoriteGistState

    var pagedList: LiveData<PagingData<Gist>>? = null

    fun loadGist() {
        pagedList = Pager(
            PagingConfig(
                PAGE_SIZE,
                PRE_FETCH_DISTANCE,
                ENABLE_PLACEHOLDERS
            ),
            INITIAL_KEY_PAGE
        ) { pagingSource }.liveData
    }

    fun favoriteClick(gist: Gist) {
        executor.execute(favoriteGistInteractor, gist)
            .subscribeBy(
                onComplete = {
                    _favoriteGistState.postValue(FavoriteGistState.Success)
                },
                onError = {
                    _favoriteGistState.postValue(FavoriteGistState.Error)
                }
            )
    }

    sealed class FavoriteGistState {
        object Error: FavoriteGistState()
        object Success: FavoriteGistState()
    }
}