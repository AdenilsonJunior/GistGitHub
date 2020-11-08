package br.com.adenilson.gist.domain.interactor

import br.com.adenilson.core.domain.Interactor
import br.com.adenilson.data.repository.GistRepository
import br.com.adenilson.gist.domain.mapper.GistMapper
import br.com.adenilson.gist.presentation.model.Gist
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface GetFavoriteGistsInteractor : Interactor<Unit, Single<List<Gist>>>

class GetFavoriteGistsInteractorImpl @Inject constructor(
    private val repository: GistRepository,
    private val mapper: GistMapper
) : GetFavoriteGistsInteractor {
    override fun execute(params: Unit): Single<List<Gist>> {
        return repository.getFavoriteGists().map { list ->
            list.map { mapper.mapTo(it) }
        }
    }
}