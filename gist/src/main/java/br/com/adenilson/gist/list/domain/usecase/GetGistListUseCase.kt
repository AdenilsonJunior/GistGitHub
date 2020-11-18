package br.com.adenilson.gist.list.domain.usecase

import br.com.adenilson.core.domain.UseCase
import br.com.adenilson.gist.common.data.repository.GistRepository
import br.com.adenilson.gist.common.domain.mapper.GistRemoteMapper
import br.com.adenilson.gist.common.domain.model.Gist
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

interface GetGistListUseCase :
    UseCase<GetGistListUseCase.Params, Observable<List<Gist>>> {
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

    override fun execute(params: GetGistListUseCase.Params): Observable<List<Gist>> {
        val repositoryRemoteCall = if (params.usernameToFilter.isBlank()) {
            repository.getGistList(page = params.page, params.perPage)
        } else {
            repository.getGistsByUsername(
                username = params.usernameToFilter,
                page = params.page,
                params.perPage
            )
        }.toObservable()
        val repositoryLocalCall = repository.getFavoriteGists()
            .onErrorComplete().toObservable()

        return Observable.combineLatest(
            repositoryRemoteCall,
            repositoryLocalCall
        ) { remoteGists, favoriteGists ->
            val gists = remoteGists.map { mapper.mapTo(it) }
            val localFavoriteGistsWebId = favoriteGists.map { it.webId }

            gists.apply {
                filter { it.webId in localFavoriteGistsWebId }.forEach { favoriteGist ->
                    favoriteGist.favorite = true
                }
            }
        }
    }
}