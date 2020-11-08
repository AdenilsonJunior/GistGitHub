package br.com.adenilson.infrastructure.dataset

import br.com.adenilson.data.model.GistModel
import br.com.adenilson.database.AppDatabase
import br.com.adenilson.infrastructure.mapper.GistEntityMapper
import br.com.adenilson.infrastructure.mapper.GistModelMapper
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface GistLocalDataSet {
    fun favoriteGist(gist: GistModel): Completable
    fun unFavoriteGist(gist: GistModel): Completable
    fun getFavoriteGists(): Single<List<GistModel>>
}

class GistLocalDataSetImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val entityMapper: GistEntityMapper,
    private val modelMapper: GistModelMapper
) : GistLocalDataSet {

    override fun favoriteGist(gist: GistModel): Completable {
        return Completable.create { emitter->
            appDatabase.favoriteGistDao().insert(entityMapper.mapTo(gist))
            emitter.onComplete()
        }
    }

    override fun unFavoriteGist(gist: GistModel): Completable {
        return Completable.create {emitter ->
            appDatabase.favoriteGistDao().delete(entityMapper.mapTo(gist))
            emitter.onComplete()
        }
    }

    override fun getFavoriteGists(): Single<List<GistModel>> {
        return Single.create { emitter ->
            val list =
                appDatabase.favoriteGistDao().getFavoriteGists().map { modelMapper.mapTo(it) }
            emitter.onSuccess(list)
        }
    }
}