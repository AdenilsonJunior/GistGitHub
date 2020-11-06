package br.com.adenilson.gist.presentation.list

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import br.com.adenilson.base.presentation.BaseViewModel
import br.com.adenilson.gist.domain.model.Gist
import br.com.adenilson.gist.domain.datasouruce.GistListDataSource
import javax.inject.Inject

class GistListViewModel @Inject constructor(
    private val pagingSource: GistListDataSource
) : BaseViewModel() {

    companion object {
        private const val PAGE_SIZE = 30
        private const val PRE_FETCH_DISTANCE = 10
        private const val ENABLE_PLACEHOLDERS = false
        private const val INITIAL_KEY_PAGE = 0
    }

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

    override fun onCleared() {
        super.onCleared()
    }
}