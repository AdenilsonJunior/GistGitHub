package br.com.adenilson.gist.domain.interactor

import br.com.adenilson.core.domain.Interactor
import br.com.adenilson.data.repository.GistRepository
import br.com.adenilson.gist.domain.mapper.GistMapper
import br.com.adenilson.gist.presentation.model.Gist
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface GetGistListInteractor :
    Interactor<GetGistListInteractor.Params, Single<List<Gist>>> {
    class Params(
        val usernameToFilter: String,
        val page: Int
    )
}

class GetGistListInteractorImpl @Inject constructor(
    private val repository: GistRepository,
    private val mapper: GistMapper
) : GetGistListInteractor {

    override fun execute(params: GetGistListInteractor.Params): Single<List<Gist>> {
        val repositoryCall = if (params.usernameToFilter.isBlank()) {
            repository.getGistList(page = params.page)
        } else {
            repository.getGistsByUsername(username = params.usernameToFilter, page = params.page)
        }

        return repositoryCall.map { list ->
            list.map { mapper.mapTo(it) }
        }
    }
}