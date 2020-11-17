package br.com.adenilson.gist.data

import br.com.adenilson.database.AppDatabase
import br.com.adenilson.database.dao.FavoriteGistDao
import br.com.adenilson.database.entity.DeleteGist
import br.com.adenilson.database.entity.FileEntity
import br.com.adenilson.database.entity.GistEntity
import br.com.adenilson.database.entity.OwnerEntity
import br.com.adenilson.network.api.GistsAPI
import br.com.adenilson.network.model.GistResponse
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException
import java.util.Date

@RunWith(MockitoJUnitRunner::class)
class GistRepositoryTest {

    private lateinit var repository: GistRepository

    private val api: GistsAPI = mock()
    private val database: AppDatabase = mock()
    private val favoriteGistDao: FavoriteGistDao = mock()

    @Before
    fun setup() {
        repository = GistRepositoryImpl(api, database)
        whenever(database.favoriteGistDao()).thenReturn(favoriteGistDao)
    }

    @Test
    fun `should favorite a gist with success`() {
        repository.favoriteGist(gistEntity).test().apply {
            assertComplete()
            assertNoErrors()

            verify(favoriteGistDao, times(1)).insert(eq(gistEntity))
            verifyNoMoreInteractions(favoriteGistDao)
            verifyZeroInteractions(api)
        }
    }

    @Test
    fun `should unFavorite a gist with success`() {
        repository.unFavoriteGist(gistEntity).test().apply {
            assertComplete()
            assertNoErrors()

            verify(favoriteGistDao, times(1)).delete(eq(DeleteGist(gistEntity.webId)))
            verifyNoMoreInteractions(favoriteGistDao)
            verifyZeroInteractions(api)
        }
    }

    @Test
    fun `should get gist list with success`() {
        val page = 1
        val perPage = 30
        whenever(api.getGists(any(), any())).thenReturn(Single.just(gistsResponse))
        repository.getGistList(page, perPage).test().apply {
            assertComplete()
            assertNoErrors()

            assertValue {
                it.size == gistsResponse.size
            }

            verify(api, times(1)).getGists(eq(page), eq(perPage))
            verifyNoMoreInteractions(api)
            verifyZeroInteractions(database)
        }
    }

    @Test
    fun `should get gist list with error`() {
        val page = 1
        val perPage = 30
        whenever(api.getGists(any(), any())).thenReturn(Single.error(IOException()))
        repository.getGistList(page, perPage).test().apply {
            assertNotComplete()
            assertError(Exception::class.java)

            verify(api, times(1)).getGists(eq(page), eq(perPage))
            verifyNoMoreInteractions(api)
            verifyZeroInteractions(database)
        }
    }

    @Test
    fun `should get gist list by username with success`() {
        val page = 1
        val perPage = 30
        val username = "username"
        whenever(api.getGistsByUsername(any(), any(), any())).thenReturn(Single.just(gistsResponse))
        repository.getGistsByUsername(username, page, perPage).test().apply {
            assertComplete()
            assertNoErrors()

            assertValue {
                it.size == gistsResponse.size
            }

            verify(api, times(1)).getGistsByUsername(eq(username), eq(page), eq(perPage))
            verifyNoMoreInteractions(api)
            verifyZeroInteractions(database)
        }
    }

    @Test
    fun `should get gist list by username with error`() {
        val page = 1
        val perPage = 30
        val username = "username"
        whenever(api.getGistsByUsername(any(), any(), any())).thenReturn(Single.error(IOException()))
        repository.getGistsByUsername(username, page, perPage).test().apply {
            assertNotComplete()
            assertError(Exception::class.java)

            verify(api, times(1)).getGistsByUsername(eq(username), eq(page), eq(perPage))
            verifyNoMoreInteractions(api)
            verifyZeroInteractions(database)
        }
    }

    @Test
    fun `should get favorite gist list with success`() {
        whenever(favoriteGistDao.getFavoriteGists()).thenReturn(gistsEntities)
        repository.getFavoriteGists().test().apply {
            assertComplete()
            assertNoErrors()

            assertValue {
                it.size == gistsEntities.size
            }

            verify(favoriteGistDao, times(1)).getFavoriteGists()
            verifyNoMoreInteractions(favoriteGistDao)
            verifyZeroInteractions(api)
        }
    }

    private val currentDate = Date()
    private val gistEntity = GistEntity(
        id = null,
        owner = OwnerEntity(
            id = null,
            name = "name",
            avatarUrl = "avatarUrl"
        ),
        files = listOf(
            FileEntity(
                id = null,
                language = "language",
                rawUrl = "rawUrl",
                type = "type",
                filename = "filename",
                size = 1
            )
        ),
        webId = "webId",
        favorite = true,
        lastUpdate = currentDate,
        description = "description"
    )

    private val gistsResponse = listOf(
        Mockito.mock(GistResponse::class.java),
        Mockito.mock(GistResponse::class.java),
        Mockito.mock(GistResponse::class.java)
    )

    private val gistsEntities = listOf(
        Mockito.mock(GistEntity::class.java),
        Mockito.mock(GistEntity::class.java),
        Mockito.mock(GistEntity::class.java)
    )
}