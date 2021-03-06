package br.com.adenilson.gist.list.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import br.com.adenilson.base.presentation.viewmodel.BaseViewModel
import br.com.adenilson.core.domain.Executor
import br.com.adenilson.gist.list.domain.datasource.GistListDataSource
import br.com.adenilson.gist.common.domain.usecase.FavoriteGistUseCase
import br.com.adenilson.gist.common.domain.model.Gist
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject
import javax.inject.Provider

class GistListViewModel @Inject constructor(
    private val executor: Executor,
    private val gistListDataSource: Provider<GistListDataSource>,
    private val favoriteGistUseCase: FavoriteGistUseCase
) : BaseViewModel() {

    companion object {
        private const val PAGE_SIZE = 30
        private const val PRE_FETCH_DISTANCE = 5
        private const val ENABLE_PLACEHOLDERS = false
        private const val INITIAL_KEY_PAGE = 0
    }

    private var _favoriteGistState: MutableLiveData<FavoriteGistState> = MutableLiveData()
    val favoriteGistState: LiveData<FavoriteGistState> = _favoriteGistState

    val pagedList: LiveData<PagingData<Gist>>? by lazy {
        loadGist()
    }

    private var usernameQuery = ""
    var dataSource: GistListDataSource? = null

    private fun loadGist(): LiveData<PagingData<Gist>>? {
        return Pager(
            PagingConfig(
                PAGE_SIZE,
                PRE_FETCH_DISTANCE,
                ENABLE_PLACEHOLDERS
            ),
            INITIAL_KEY_PAGE
        ) {
            gistListDataSource.get().apply {
                perPage = PAGE_SIZE
                usernameToFilter = usernameQuery
                dataSource = this
            }
        }.liveData.cachedIn(viewModelScope)
    }

    fun favoriteClick(gist: Gist) {
        executor.execute(favoriteGistUseCase, gist)
            .subscribeBy(
                onComplete = {
                    _favoriteGistState.postValue(FavoriteGistState.Success(gist))
                },
                onError = {
                    _favoriteGistState.postValue(FavoriteGistState.Error)
                }
            )
    }

    fun makeSearch(query: String) {
        dataSource?.run {
            usernameQuery = query
            invalidate()
        }
    }

    sealed class FavoriteGistState {
        object Error : FavoriteGistState()
        data class Success(val gist: Gist) : FavoriteGistState()
    }
}