package br.com.adenilson.gist.list.domain.datasource

import androidx.paging.rxjava3.RxPagingSource
import br.com.adenilson.base.domain.exception.UserHasNoGistsException
import br.com.adenilson.core.domain.Executor
import br.com.adenilson.gist.list.domain.interactor.GetGistListInteractor
import br.com.adenilson.gist.list.domain.interactor.UpdateIsFavoriteGistsInteractor
import br.com.adenilson.gist.commons.domain.model.Gist
import io.reactivex.rxjava3.core.Single
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
                    if (userHasGists(gists, page)) {
                        LoadResult.Page(
                            data = gists,
                            prevKey = null,
                            nextKey = if (gists.isNotEmpty()) page + 1 else null
                        )
                    } else {
                        LoadResult.Error(UserHasNoGistsException())
                    }
                } catch (e: Exception) {
                    LoadResult.Error(e)
                }
            }
            .onErrorResumeNext {
                Single.just(LoadResult.Error(it))
            }
    }

    private fun userHasGists(
        gists: List<Gist>,
        page: Int
    ) = gists.isNotEmpty() || page != 0
}