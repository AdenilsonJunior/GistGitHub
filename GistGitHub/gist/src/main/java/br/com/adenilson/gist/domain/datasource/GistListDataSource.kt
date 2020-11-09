package br.com.adenilson.gist.domain.datasource

import androidx.paging.rxjava3.RxPagingSource
import br.com.adenilson.core.domain.Executor
import br.com.adenilson.gist.domain.interactor.GetGistListInteractor
import br.com.adenilson.gist.domain.interactor.UpdateIsFavoriteGistsInteractor
import br.com.adenilson.gist.presentation.model.Gist
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GistListDataSource @Inject constructor(
    private val executor: Executor,
    private val getGistListInteractor: GetGistListInteractor,
    private val updateIsFavoriteGistsInteractor: UpdateIsFavoriteGistsInteractor
) : RxPagingSource<Int, Gist>() {

    var usernameToFilter = ""

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Gist>> {
        val page = params.key ?: 0
        return executor.execute(
            getGistListInteractor,
            GetGistListInteractor.Params(usernameToFilter, page)
        )
            .flatMap { executor.execute(updateIsFavoriteGistsInteractor, it) }
            .map { gists ->
                try {
                    LoadResult.Page(
                        data = gists,
                        prevKey = null,
                        nextKey = if(gists.isNotEmpty()) page + 1 else  null
                    )
                } catch (e: Exception) {
                    LoadResult.Error<Int, Gist>(e)
                }
            }
            .onErrorResumeNext {
                Single.just(LoadResult.Error(it))
            }
    }
}