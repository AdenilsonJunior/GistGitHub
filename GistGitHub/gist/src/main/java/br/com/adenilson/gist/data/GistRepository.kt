package br.com.adenilson.gist.data

import br.com.adenilson.database.AppDatabase
import br.com.adenilson.database.entity.DeleteGist
import br.com.adenilson.database.entity.GistEntity
import br.com.adenilson.network.api.GistsAPI
import br.com.adenilson.network.model.GistResponse
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

interface GistRepository {
    fun getGistList(page: Int): Single<List<GistResponse>>
    fun unFavoriteGist(gist: GistEntity): Completable
    fun favoriteGist(gist: GistEntity): Completable
    fun getFavoriteGists(): Single<List<GistEntity>>
    fun getGistsByUsername(username: String, page: Int): Single<List<GistResponse>>
}

class GistRepositoryImpl @Inject constructor(
    private val api: GistsAPI,
    private val database: AppDatabase
) : GistRepository {

    override fun getGistList(page: Int): Single<List<GistResponse>> {
        return api.getGists(page)
            .onErrorResumeNext {
                if (it is HttpException) {
                    Single.error(IOException(it.message))
                } else {
                    Single.error(it)
                }
            }
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

    override fun getGistsByUsername(username: String, page: Int): Single<List<GistResponse>> {
        return api.getGistsByUsername(username, page)
            .onErrorResumeNext {
                if (it is HttpException) {
                    Single.error(IOException(it.message))
                } else {
                    Single.error(it)
                }
            }
    }
}