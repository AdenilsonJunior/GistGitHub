package br.com.adenilson.gist.domain.datasource

import androidx.paging.rxjava3.RxPagingSource
import br.com.adenilson.base.domain.exception.UserNotFoundException
import br.com.adenilson.core.domain.Executor
import br.com.adenilson.gist.domain.interactor.GetGistListInteractor
import br.com.adenilson.gist.domain.interactor.UpdateIsFavoriteGistsInteractor
import br.com.adenilson.gist.presentation.model.Gist
import io.reactivex.rxjava3.core.Single
import java.io.IOException
import javax.inject.Inject

class GistListDataSource @Inject constructor(
    private val executor: Executor,
    private val getGistListInteractor: GetGistListInteractor,
    private val updateIsFavoriteGistsInteractor: UpdateIsFavoriteGistsInteractor
) : RxPagingSource<Int, Gist>() {

    companion object {
        private const val PER_PAGE_DEFAULT = 30
    }

    var usernameToFilter = ""
    var perPage = PER_PAGE_DEFAULT

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Gist>> {
        val page = params.key ?: 0
        return executor.execute(
            getGistListInteractor,
            GetGistListInteractor.Params(usernameToFilter, page, perPage)
        )
            .flatMap { executor.execute(updateIsFavoriteGistsInteractor, it) }
            .map { gists ->
                try {
                    LoadResult.Page(
                        data = gists,
                        prevKey = null,
                        nextKey = if (gists.isNotEmpty()) page + 1 else null
                    )
                } catch (e: Exception) {
                    if (isUserNotFound(e)) {
                        LoadResult.Error(UserNotFoundException())
                    } else {
                        LoadResult.Error(e)
                    }
                }
            }
            .onErrorResumeNext {
                val exception = if (isUserNotFound(it)) {
                    UserNotFoundException()
                } else {
                    it
                }
                Single.just(LoadResult.Error(exception))
            }
    }

    private fun isUserNotFound(e: Throwable): Boolean {
        return e is IOException && e.message?.contains("404") == true && usernameToFilter.isNotBlank()
    }
}