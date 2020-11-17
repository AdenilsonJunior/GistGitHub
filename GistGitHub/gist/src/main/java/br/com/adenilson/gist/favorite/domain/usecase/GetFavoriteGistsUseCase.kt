package br.com.adenilson.gist.favorite.domain.usecase

import br.com.adenilson.core.domain.UseCase
import br.com.adenilson.gist.common.data.repository.GistRepository
import br.com.adenilson.gist.favorite.domain.mapper.GistLocalMapper
import br.com.adenilson.gist.common.domain.model.Gist
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface GetFavoriteGistsUseCase : UseCase<Unit, Single<List<Gist>>>

class GetFavoriteGistsUseCaseImpl @Inject constructor(
    private val repository: GistRepository,
    private val mapper: GistLocalMapper
) : GetFavoriteGistsUseCase {
    override fun execute(params: Unit): Single<List<Gist>> {
        return repository.getFavoriteGists().map { list ->
            list.map { mapper.mapTo(it) }
        }
    }
}