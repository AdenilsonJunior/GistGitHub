package br.com.adenilson.network.api

import br.com.adenilson.network.model.GistResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GistsAPI {

    @GET("gists/public")
    fun getGists(@Query("page") page: Int): Single<List<GistResponse>>
}