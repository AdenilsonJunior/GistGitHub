package br.com.adenilson.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.adenilson.database.entity.DeleteGist
import br.com.adenilson.database.entity.GistEntity

@Dao
interface FavoriteGistDao {

    @Query("SELECT * FROM gist WHERE gist.favorite = 1")
    fun getFavoriteGists(): List<GistEntity>

    @Delete(entity = GistEntity::class)
    fun delete(vararg webId: DeleteGist)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favoriteGistEntity: GistEntity)
}