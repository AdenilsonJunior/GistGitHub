package br.com.adenilson.gist.list.domain.usecase

import br.com.adenilson.core.domain.UseCase
import br.com.adenilson.gist.common.data.repository.GistRepository
import br.com.adenilson.gist.common.domain.model.Gist
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface UpdateIsFavoriteGistsUseCase : UseCase<List<Gist>, Single<List<Gist>>>

class UpdateIsFavoriteGistsUseCaseImpl @Inject constructor(
    private val repository: GistRepository
) : UpdateIsFavoriteGistsUseCase {

    override fun execute(params: List<Gist>): Single<List<Gist>> {
        return repository.getFavoriteGists().map { gistModels ->
            val localFavoriteGistsWebId = gistModels.map { it.webId }
            params.apply {
                filter { it.webId in localFavoriteGistsWebId }.forEach { favoriteGist ->
                    favoriteGist.favorite = true
                }
            }
        }
    }
}
