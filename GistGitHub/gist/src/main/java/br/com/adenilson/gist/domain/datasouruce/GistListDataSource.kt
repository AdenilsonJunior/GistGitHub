package br.com.adenilson.gist.domain.datasouruce

import androidx.paging.rxjava3.RxPagingSource
import br.com.adenilson.base.domain.Executor
import br.com.adenilson.gist.domain.interactor.GetGistListInteractor
import br.com.adenilson.gist.domain.model.Gist
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GistListDataSource @Inject constructor(
    private val executor: Executor,
    private val interactor: GetGistListInteractor
) : RxPagingSource<Int, Gist>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Gist>> {
        val page = params.key ?: 0
        return executor.execute(interactor, GetGistListInteractor.Params(page))
            .map { gists ->
                try {
                    LoadResult.Page(
                        data = gists,
                        prevKey = null,
                        nextKey = page + 1
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