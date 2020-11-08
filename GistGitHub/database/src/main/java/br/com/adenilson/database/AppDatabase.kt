package br.com.adenilson.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.adenilson.database.converter.Converters
import br.com.adenilson.database.dao.FavoriteGistDao
import br.com.adenilson.database.entity.GistEntity
import br.com.adenilson.database.entity.FileEntity
import br.com.adenilson.database.entity.OwnerEntity


@Database(
    entities = [
        GistEntity::class,
        OwnerEntity::class,
        FileEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "gist-db"
    }

    abstract fun favoriteGistDao(): FavoriteGistDao
}