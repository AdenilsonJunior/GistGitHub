package br.com.adenilson.network.api

import br.com.adenilson.network.model.GistResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GistsAPI {

    @GET("gists/public?per_page=30")
    fun getGists(@Query("page") page: Int): Single<List<GistResponse>>

    @GET("users/{username}/gists?per_page_30")
    fun getGistsByUsername(
        @Path("username") username: String,
        @Query("page") page: Int
    ): Single<List<GistResponse>>
}