package br.com.adenilson.infrastructure.dataset

import br.com.adenilson.data.model.GistModel
import br.com.adenilson.infrastructure.mapper.GistModelListMapper
import br.com.adenilson.network.api.GistsAPI
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface GistRemoteDataSet {
    fun getGistList(page: Int): Single<List<GistModel>>
}

class GistRemoteDataSetImpl @Inject constructor(
    private val api: GistsAPI,
    private val mapper: GistModelListMapper
) : GistRemoteDataSet {

    override fun getGistList(page: Int): Single<List<GistModel>> {
        return api.getGists(page).map(mapper::mapTo)
    }
}