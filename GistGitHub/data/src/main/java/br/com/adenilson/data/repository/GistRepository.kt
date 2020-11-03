package br.com.adenilson.data.repository

import br.com.adenilson.network.GistRemoteDataSet
import br.com.adenilson.network.model.GistResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface GistRepository {
    fun getGistList(): Single<List<GistResponse>>
}

class GistRepositoryImpl @Inject constructor(
    private val gistRemoteDataSet: GistRemoteDataSet
) : GistRepository {

    override fun getGistList(): Single<List<GistResponse>> {
        return gistRemoteDataSet.getGistList()
    }
}