package br.com.adenilson.gist.list.domain.interactor

import br.com.adenilson.base.domain.Interactor
import br.com.adenilson.data.repository.GistRepository
import br.com.adenilson.gist.list.domain.mapper.GistListMapper
import br.com.adenilson.gist.list.domain.model.Gist
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface GetGistListInteractor : Interactor<Unit, Single<List<Gist>>>

class GetGistListInteractorImpl @Inject constructor(
    private val repository: GistRepository,
    private val mapper: GistListMapper
) : GetGistListInteractor {

    override fun execute(params: Unit): Single<List<Gist>> {
        return repository.getGistList().map(mapper::mapToPresentation)
    }
}