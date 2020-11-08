package br.com.adenilson.infrastructure.dataset

import br.com.adenilson.data.model.GistModel
import br.com.adenilson.database.AppDatabase
import br.com.adenilson.infrastructure.mapper.GistEntityMapper
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

interface GistLocalDataSet {
    fun favoriteGist(gist: GistModel): Completable
    fun unFavoriteGist(gist: GistModel): Completable
}

class GistLocalDataSetImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val mapper: GistEntityMapper
) : GistLocalDataSet {

    override fun favoriteGist(gist: GistModel): Completable {
        return Completable.create {
            appDatabase.favoriteGistDao().insert(mapper.mapTo(gist))
            it.onComplete()
        }
    }

    override fun unFavoriteGist(gist: GistModel): Completable {
        return Completable.create {
            appDatabase.favoriteGistDao().delete(mapper.mapTo(gist))
            it.onComplete()
        }
    }
}