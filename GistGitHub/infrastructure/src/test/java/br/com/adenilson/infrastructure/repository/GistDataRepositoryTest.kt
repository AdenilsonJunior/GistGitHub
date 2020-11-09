package br.com.adenilson.infrastructure.repository

import br.com.adenilson.data.model.FileModel
import br.com.adenilson.data.model.GistModel
import br.com.adenilson.data.model.OwnerModel
import br.com.adenilson.data.repository.GistRepository
import br.com.adenilson.infrastructure.dataset.GistLocalDataSet
import br.com.adenilson.infrastructure.dataset.GistRemoteDataSet
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException
import java.util.Date

@RunWith(MockitoJUnitRunner::class)
class GistDataRepositoryTest {

    private lateinit var repository: GistRepository
    private val remoteDataSet: GistRemoteDataSet = mock()
    private val localDataSet: GistLocalDataSet = mock()

    @Before
    fun setup() {
        repository = GistDataRepository(remoteDataSet, localDataSet)
    }

    @Test
    fun `should favorite a gist with success`() {
        whenever(localDataSet.favoriteGist(any())).thenReturn(Completable.complete())
        repository.favoriteGist(gistModel).test().apply {
            assertComplete()
            assertNoErrors()

            verify(localDataSet, times(1)).favoriteGist(eq(gistModel))
            verifyNoMoreInteractions(localDataSet)
            verifyZeroInteractions(remoteDataSet)
        }
    }

    @Test
    fun `should favorite a gist with error`() {
        whenever(localDataSet.favoriteGist(any())).thenReturn(Completable.error(Exception()))
        repository.favoriteGist(gistModel).test().apply {
            assertNotComplete()
            assertError(Exception::class.java)

            verify(localDataSet, times(1)).favoriteGist(eq(gistModel))
            verifyNoMoreInteractions(localDataSet)
            verifyZeroInteractions(remoteDataSet)
        }
    }

    @Test
    fun `should unFavorite a gist with success`() {
        whenever(localDataSet.unFavoriteGist(any())).thenReturn(Completable.complete())
        repository.unFavoriteGist(gistModel).test().apply {
            assertComplete()
            assertNoErrors()

            verify(localDataSet, times(1)).unFavoriteGist(eq(gistModel))
            verifyNoMoreInteractions(localDataSet)
            verifyZeroInteractions(remoteDataSet)
        }
    }

    @Test
    fun `should unFavorite a gist with error`() {
        whenever(localDataSet.unFavoriteGist(any())).thenReturn(Completable.error(Exception()))
        repository.unFavoriteGist(gistModel).test().apply {
            assertNotComplete()
            assertError(Exception::class.java)

            verify(localDataSet, times(1)).unFavoriteGist(eq(gistModel))
            verifyNoMoreInteractions(localDataSet)
            verifyZeroInteractions(remoteDataSet)
        }
    }

    @Test
    fun `should get gist list with success`() {
        val page = 1
        whenever(remoteDataSet.getGistList(any())).thenReturn(Single.just(gistModelsList))
        repository.getGistList(page).test().apply {
            assertComplete()
            assertNoErrors()

            assertValue {
                it.size == gistModelsList.size
            }

            verify(remoteDataSet, times(1)).getGistList(eq(page))
            verifyNoMoreInteractions(remoteDataSet)
            verifyZeroInteractions(localDataSet)
        }
    }

    @Test
    fun `should get gist list with error`() {
        val page = 1
        whenever(remoteDataSet.getGistList(any())).thenReturn(Single.error(IOException()))
        repository.getGistList(page).test().apply {
            assertNotComplete()
            assertError(Exception::class.java)

            verify(remoteDataSet, times(1)).getGistList(eq(page))
            verifyNoMoreInteractions(remoteDataSet)
            verifyZeroInteractions(localDataSet)
        }
    }

    @Test
    fun `should get gist list by username with success`() {
        val page = 1
        val username = "username"
        whenever(remoteDataSet.getGistsByUsername(any(), any())).thenReturn(
            Single.just(
                gistModelsList
            )
        )
        repository.getGistsByUsername(username, page).test().apply {
            assertComplete()
            assertNoErrors()

            assertValue {
                it.size == gistModelsList.size
            }

            verify(remoteDataSet, times(1)).getGistsByUsername(eq(username), eq(page))
            verifyNoMoreInteractions(remoteDataSet)
            verifyZeroInteractions(localDataSet)
        }
    }

    @Test
    fun `should get gist list by username with error`() {
        val page = 1
        val username = "username"
        whenever(
            remoteDataSet.getGistsByUsername(
                any(),
                any()
            )
        ).thenReturn(Single.error(IOException()))
        repository.getGistsByUsername(username, page).test().apply {
            assertNotComplete()
            assertError(Exception::class.java)

            verify(remoteDataSet, times(1)).getGistsByUsername(eq(username), eq(page))
            verifyNoMoreInteractions(remoteDataSet)
            verifyZeroInteractions(localDataSet)
        }
    }

    @Test
    fun `should get favorite gist list by username with success`() {
        whenever(localDataSet.getFavoriteGists()).thenReturn(Single.just(gistModelsList))
        repository.getFavoriteGists().test().apply {
            assertComplete()
            assertNoErrors()

            assertValue {
                it.size == gistModelsList.size
            }

            verify(localDataSet, times(1)).getFavoriteGists()
            verifyNoMoreInteractions(localDataSet)
            verifyZeroInteractions(remoteDataSet)
        }
    }

    @Test
    fun `should get favorite gist list by username with error`() {
        whenever(localDataSet.getFavoriteGists()).thenReturn(Single.error(Exception()))
        repository.getFavoriteGists().test().apply {
            assertNotComplete()
            assertError(Exception::class.java)

            verify(localDataSet, times(1)).getFavoriteGists()
            verifyNoMoreInteractions(localDataSet)
            verifyZeroInteractions(remoteDataSet)
        }
    }


    private val currentDate = Date()
    private val gistModel = GistModel(
        id = null,
        owner = OwnerModel(
            id = null,
            name = "name",
            avatarUrl = "avatarUrl"
        ),
        files = listOf(
            FileModel(
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

    private val gistModelsList = listOf(
        Mockito.mock(GistModel::class.java),
        Mockito.mock(GistModel::class.java)
    )
}