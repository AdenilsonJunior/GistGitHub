package br.com.adenilson.gist.list.domain.interactor

import br.com.adenilson.core.domain.Interactor
import br.com.adenilson.gist.commons.data.repository.GistRepository
import br.com.adenilson.gist.commons.domain.mapper.GistRemoteMapper
import br.com.adenilson.gist.commons.domain.model.Gist
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface GetGistListInteractor :
    Interactor<GetGistListInteractor.Params, Single<List<Gist>>> {
    data class Params(
        val usernameToFilter: String,
        val page: Int,
        val perPage: Int
    )
}

class GetGistListInteractorImpl @Inject constructor(
    private val repository: GistRepository,
    private val mapper: GistRemoteMapper
) : GetGistListInteractor {

    override fun execute(params: GetGistListInteractor.Params): Single<List<Gist>> {
        val repositoryCall = if (params.usernameToFilter.isBlank()) {
            repository.getGistList(page = params.page, params.perPage)
        } else {
            repository.getGistsByUsername(
                username = params.usernameToFilter,
                page = params.page,
                params.perPage
            )
        }

        return repositoryCall.map { list ->
            list.map { mapper.mapTo(it) }
        }
    }
}