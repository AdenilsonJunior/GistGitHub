package br.com.adenilson.network

import br.com.adenilson.network.api.GistsAPI
import br.com.adenilson.network.model.GistResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface GistRemoteDataSet {
    fun getGistList(page: Int): Single<List<GistResponse>>
}

class GistRemoteDataSetImpl @Inject constructor(
    private val api: GistsAPI
) : GistRemoteDataSet {

    override fun getGistList(page: Int): Single<List<GistResponse>> {
        return api.getGists(page)
    }
}