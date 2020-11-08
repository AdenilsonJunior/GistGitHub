package br.com.adenilson.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.adenilson.database.entity.GistEntity

@Dao
interface FavoriteGistDao {

    @Query("SELECT * FROM gist")
    fun getFavoriteGists(): List<GistEntity>

    @Delete
    fun delete(favoriteGistEntity: GistEntity)

    @Insert
    fun insert(favoriteGistEntity: GistEntity)
}