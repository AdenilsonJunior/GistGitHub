package br.com.adenilson.data.repository

import br.com.adenilson.data.model.GistModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface GistRepository {
    fun getGistList(page: Int): Single<List<GistModel>>
    fun unFavoriteGist(gist: GistModel): Completable
    fun favoriteGist(gist: GistModel): Completable
    fun getFavoriteGists(): Single<List<GistModel>>
}