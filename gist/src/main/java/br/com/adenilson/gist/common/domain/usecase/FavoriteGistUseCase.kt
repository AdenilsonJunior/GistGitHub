package br.com.adenilson.gist.common.domain.usecase

import br.com.adenilson.core.domain.UseCase
import br.com.adenilson.gist.common.data.repository.GistRepository
import br.com.adenilson.gist.common.domain.mapper.GistEntityMapper
import br.com.adenilson.gist.common.domain.model.Gist
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

interface FavoriteGistUseCase : UseCase<Gist, Completable>

class FavoriteGistUseCaseImpl @Inject constructor(
    private val repository: GistRepository,
    private val mapper: GistEntityMapper
) : FavoriteGistUseCase {

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