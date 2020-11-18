package br.com.adenilson.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import br.com.adenilson.core.extensions.parseToString
import br.com.adenilson.database.dao.FavoriteGistDao
import br.com.adenilson.database.entity.DeleteGist
import br.com.adenilson.database.entity.FileEntity
import br.com.adenilson.database.entity.GistEntity
import br.com.adenilson.database.entity.OwnerEntity
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.Date

@RunWith(AndroidJUnit4::class)
class FavoriteGistDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: FavoriteGistDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()
        dao = db.favoriteGistDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insert() {
        val currentDate = Date()
        val entity = GistEntity(
            id = null,
            webId = "webId",
            owner = OwnerEntity(
                id = null,
                name = "name",
                avatarUrl = "avatarUrl"
            ),
            favorite = true,
            lastUpdate = currentDate,
            description = "description",
            files = listOf(
                FileEntity(
                    id = null,
                    language = "language",
                    size = 1,
                    rawUrl = "rawUrl",
                    type = "type",
                    filename = "filename"
                )
            )
        )
        dao.insert(entity)
        val result = dao.getFavoriteGists().first()
        assertEquals(1L, result.id)
        assertEquals(entity.owner, result.owner)
        assertEquals(entity.files, result.files)
        assertEquals(entity.description, result.description)
        assertEquals(entity.webId, result.webId)
        assertEquals(entity.favorite, result.favorite)
        assertEquals(entity.lastUpdate?.parseToString(), result.lastUpdate?.parseToString())
    }

    @Test
    @Throws(Exception::class)
    fun delete() {
        val currentDate = Date()
        val entity = GistEntity(
            id = 1,
            webId = "webId",
            owner = OwnerEntity(
                id = null,
                name = "name",
                avatarUrl = "avatarUrl"
            ),
            favorite = true,
            lastUpdate = currentDate,
            description = "description",
            files = listOf(
                FileEntity(
                    id = null,
                    language = "language",
                    size = 1,
                    rawUrl = "rawUrl",
                    type = "type",
                    filename = "filename"
                )
            )
        )
        val deleteGist = DeleteGist(
            webId = "webId"
        )
        dao.insert(entity)
        dao.delete(deleteGist)
        val result = dao.getFavoriteGists()
        assertEquals(listOf<GistEntity>(), result)
    }

    @Test
    @Throws(Exception::class)
    fun getOnlyFavoriteGists() {
        val currentDate = Date()
        val entity1 = GistEntity(
            id = null,
            webId = "webId1",
            owner = OwnerEntity(
                id = null,
                name = "name",
                avatarUrl = "avatarUrl"
            ),
            favorite = true,
            lastUpdate = currentDate,
            description = "description",
            files = listOf(
                FileEntity(
                    id = null,
                    language = "language",
                    size = 1,
                    rawUrl = "rawUrl",
                    type = "type",
                    filename = "filename"
                )
            )
        )
        val entity2 = GistEntity(
            id = null,
            webId = "webId2",
            owner = OwnerEntity(
                id = null,
                name = "name",
                avatarUrl = "avatarUrl"
            ),
            favorite = true,
            lastUpdate = currentDate,
            description = "description",
            files = listOf(
                FileEntity(
                    id = null,
                    language = "language",
                    size = 1,
                    rawUrl = "rawUrl",
                    type = "type",
                    filename = "filename"
                )
            )
        )
        val unFavorite = GistEntity(
            id = null,
            webId = "webId3",
            owner = OwnerEntity(
                id = null,
                name = "name",
                avatarUrl = "avatarUrl"
            ),
            favorite = false,
            lastUpdate = currentDate,
            description = "description",
            files = listOf(
                FileEntity(
                    id = null,
                    language = "language",
                    size = 1,
                    rawUrl = "rawUrl",
                    type = "type",
                    filename = "filename"
                )
            )
        )
        dao.insert(entity1)
        dao.insert(entity2)
        dao.insert(unFavorite)

        val result = dao.getFavoriteGists()
        assertEquals(2, result.size)
    }
}