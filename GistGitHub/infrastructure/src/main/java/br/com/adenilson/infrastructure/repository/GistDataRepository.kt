package br.com.adenilson.infrastructure.repository

import br.com.adenilson.data.model.GistModel
import br.com.adenilson.data.repository.GistRepository
import br.com.adenilson.infrastructure.dataset.GistLocalDataSet
import br.com.adenilson.infrastructure.dataset.GistRemoteDataSet
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GistDataRepository @Inject constructor(
    private val remoteDataSet: GistRemoteDataSet,
    private val localDataSet: GistLocalDataSet
) : GistRepository {

    override fun getGistList(page: Int): Single<List<GistModel>> {
        return remoteDataSet.getGistList(page)
    }

    override fun unFavoriteGist(gist: GistModel): Completable {
        return localDataSet.unFavoriteGist(gist)
    }

    override fun favoriteGist(gist: GistModel): Completable {
        return localDataSet.favoriteGist(gist)
    }

    override fun getFavoriteGists(): Single<List<GistModel>> {
        return localDataSet.getFavoriteGists()
    }
}