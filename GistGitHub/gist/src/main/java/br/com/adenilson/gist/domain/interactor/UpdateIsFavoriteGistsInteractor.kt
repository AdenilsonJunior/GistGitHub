package br.com.adenilson.gist.domain.interactor

import br.com.adenilson.core.domain.Interactor
import br.com.adenilson.gist.data.GistRepository
import br.com.adenilson.gist.presentation.model.Gist
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface UpdateIsFavoriteGistsInteractor : Interactor<List<Gist>, Single<List<Gist>>>

class UpdateIsFavoriteGistsInteractorImpl @Inject constructor(
    private val repository: GistRepository
) : UpdateIsFavoriteGistsInteractor {

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
