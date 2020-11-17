package br.com.adenilson.gist.common.data.repository

import br.com.adenilson.base.domain.exception.ConnectionException
import br.com.adenilson.base.domain.exception.UserNotFoundException
import br.com.adenilson.database.AppDatabase
import br.com.adenilson.database.entity.DeleteGist
import br.com.adenilson.database.entity.GistEntity
import br.com.adenilson.network.api.GistsAPI
import br.com.adenilson.network.response.GistResponse
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.HttpException
import javax.inject.Inject

interface GistRepository {
    fun getGistList(page: Int, perPage: Int): Single<List<GistResponse>>
    fun unFavoriteGist(gist: GistEntity): Completable
    fun favoriteGist(gist: GistEntity): Completable
    fun getFavoriteGists(): Single<List<GistEntity>>
    fun getGistsByUsername(username: String, page: Int, perPage: Int): Single<List<GistResponse>>
}

class GistRepositoryImpl @Inject constructor(
    private val api: GistsAPI,
    private val database: AppDatabase
) : GistRepository {

    override fun getGistList(page: Int, perPage: Int): Single<List<GistResponse>> {
        return api.getGists(page, perPage)
            .handleException()
    }

    override fun unFavoriteGist(gist: GistEntity): Completable {
        return Completable.create { emitter ->
            database.favoriteGistDao().delete(DeleteGist(gist.webId))
            emitter.onComplete()
        }
    }

    override fun favoriteGist(gist: GistEntity): Completable {
        return Completable.create { emitter ->
            gist.favorite = true
            database.favoriteGistDao().insert(gist)
            emitter.onComplete()
        }
    }

    override fun getFavoriteGists(): Single<List<GistEntity>> {
        return Single.create { emitter ->
            val list =
                database.favoriteGistDao().getFavoriteGists()
            emitter.onSuccess(list)
        }
    }

    override fun getGistsByUsername(
        username: String,
        page: Int,
        perPage: Int
    ): Single<List<GistResponse>> {
        return api.getGistsByUsername(username, page, perPage)
            .handleException()
    }

    private fun handleHttpException(exception: HttpException) = Single.error<List<GistResponse>>(
        if (exception.isUserNotFound()) {
            UserNotFoundException()
        } else {
            ConnectionException(exception.code())
        }
    )

    private fun Single<List<GistResponse>>.handleException() =
        onErrorResumeNext {
            when (it) {
                is HttpException -> handleHttpException(it)
                else -> Single.error(it)
            }
        }

    private fun HttpException.isUserNotFound() = code() == 404
}