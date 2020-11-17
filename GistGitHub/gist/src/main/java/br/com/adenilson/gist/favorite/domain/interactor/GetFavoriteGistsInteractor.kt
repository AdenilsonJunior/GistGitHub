package br.com.adenilson.gist.favorite.domain.interactor

import br.com.adenilson.core.domain.Interactor
import br.com.adenilson.gist.commons.data.repository.GistRepository
import br.com.adenilson.gist.favorite.domain.mapper.GistLocalMapper
import br.com.adenilson.gist.commons.domain.model.Gist
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface GetFavoriteGistsInteractor : Interactor<Unit, Single<List<Gist>>>

class GetFavoriteGistsInteractorImpl @Inject constructor(
    private val repository: GistRepository,
    private val mapper: GistLocalMapper
) : GetFavoriteGistsInteractor {
    override fun execute(params: Unit): Single<List<Gist>> {
        return repository.getFavoriteGists().map { list ->
            list.map { mapper.mapTo(it) }
        }
    }
}