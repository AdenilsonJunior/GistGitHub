package br.com.adenilson.gist.domain.interactor

import br.com.adenilson.core.domain.Interactor
import br.com.adenilson.gist.data.GistRepository
import br.com.adenilson.gist.domain.mapper.GistEntityMapper
import br.com.adenilson.gist.presentation.model.Gist
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

interface FavoriteGistInteractor : Interactor<Gist, Completable>

class FavoriteGistInteractorImpl @Inject constructor(
    private val repository: GistRepository,
    private val mapper: GistEntityMapper
) : FavoriteGistInteractor {

    override fun execute(params: Gist): Completable {
        return if (params.favorite) {
            repository.unFavoriteGist(mapper.mapTo(params))
        } else {
            repository.favoriteGist(mapper.mapTo(params))
        }.doOnComplete {
            params.favorite = !params.favorite
        }
    }
}