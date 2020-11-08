package br.com.adenilson.infrastructure.dataset

import br.com.adenilson.data.model.GistModel
import br.com.adenilson.infrastructure.mapper.GistModelListMapper
import br.com.adenilson.network.api.GistsAPI
import io.reactivex.rxjava3.core.Single
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

interface GistRemoteDataSet {
    fun getGistList(page: Int): Single<List<GistModel>>
    fun getGistsByUsername(username: String, page: Int): Single<List<GistModel>>
}

class GistRemoteDataSetImpl @Inject constructor(
    private val api: GistsAPI,
    private val mapper: GistModelListMapper
) : GistRemoteDataSet {

    override fun getGistList(page: Int): Single<List<GistModel>> {
        return api.getGists(page).map(mapper::mapTo)
            .onErrorResumeNext {
                if (it is HttpException) {
                    Single.error(IOException(it.message))
                } else {
                    Single.error(it)
                }
            }
    }

    override fun getGistsByUsername(username: String, page: Int): Single<List<GistModel>> {
        return api.getGistsByUsername(username, page).map(mapper::mapTo)
            .onErrorResumeNext {
                if (it is HttpException) {
                    Single.error(IOException(it.message))
                } else {
                    Single.error(it)
                }
            }
    }
}