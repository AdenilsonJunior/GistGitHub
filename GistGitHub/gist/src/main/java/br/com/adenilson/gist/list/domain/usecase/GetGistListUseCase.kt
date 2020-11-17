package br.com.adenilson.gist.list.domain.usecase

import br.com.adenilson.core.domain.UseCase
import br.com.adenilson.gist.common.data.repository.GistRepository
import br.com.adenilson.gist.common.domain.mapper.GistRemoteMapper
import br.com.adenilson.gist.common.domain.model.Gist
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface GetGistListUseCase :
    UseCase<GetGistListUseCase.Params, Single<List<Gist>>> {
    data class Params(
        val usernameToFilter: String,
        val page: Int,
        val perPage: Int
    )
}

class GetGistListUseCaseImpl @Inject constructor(
    private val repository: GistRepository,
    private val mapper: GistRemoteMapper
) : GetGistListUseCase {

    override fun execute(params: GetGistListUseCase.Params): Single<List<Gist>> {
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