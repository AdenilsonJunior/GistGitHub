package br.com.adenilson.infrastructure.dataset

import br.com.adenilson.data.model.FileModel
import br.com.adenilson.data.model.GistModel
import br.com.adenilson.data.model.OwnerModel
import br.com.adenilson.database.AppDatabase
import br.com.adenilson.database.dao.FavoriteGistDao
import br.com.adenilson.database.entity.GistEntity
import br.com.adenilson.infrastructure.mapper.GistEntityMapper
import br.com.adenilson.infrastructure.mapper.GistModelMapper
import br.com.adenilson.infrastructure.mapper.GistModelMapperTest
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.Date

@RunWith(MockitoJUnitRunner::class)
class GistLocalDataSetTest {


    private lateinit var localDataSet: GistLocalDataSet

    private val appDatabase: AppDatabase = mock()
    private val entityMapper: GistEntityMapper = mock()
    private val modelMapper: GistModelMapper = mock()
    private val favoriteGistDao: FavoriteGistDao = mock()

    @Before
    fun setup() {
        localDataSet = GistLocalDataSetImpl(appDatabase, entityMapper, modelMapper)
        whenever(appDatabase.favoriteGistDao()).thenReturn(favoriteGistDao)
    }

    @Test
    fun `should favorite a gist with success`() {
        val currentDate = Date()
        val gistModel = GistModel(
            id = null,
            files = listOf(
                FileModel(
                    id = null,
                    filename = "filename",
                    type = "type",
                    rawUrl = "rawUrl",
                    language = "language",
                    size = 1
                )
            ),
            description = "description",
            lastUpdate = currentDate,
            favorite = false,
            owner = OwnerModel(
                id = null,
                avatarUrl = "avatarUrl",
                name = "name"
            ),
            webId = "webId"
        )
        val entityMock = Mockito.mock(GistEntity::class.java)
        whenever(entityMapper.mapTo(any())).thenReturn(entityMock)
        localDataSet.favoriteGist(gistModel).test().run {
            assertComplete()
            assertNoErrors()

            verify(appDatabase, times(1)).favoriteGistDao()
            verify(favoriteGistDao, times(1)).insert(any())
            verify(entityMapper, times(1)).mapTo(eq(gistModel))
            verifyNoMoreInteractions(appDatabase, favoriteGistDao, entityMapper)
        }
    }

    @Test
    fun `should unFavorite a gist with success`() {
        val currentDate = Date()
        val gistModel = GistModel(
            id = null,
            files = listOf(
                FileModel(
                    id = null,
                    filename = "filename",
                    type = "type",
                    rawUrl = "rawUrl",
                    language = "language",
                    size = 1
                )
            ),
            description = "description",
            lastUpdate = currentDate,
            favorite = true,
            owner = OwnerModel(
                id = null,
                avatarUrl = "avatarUrl",
                name = "name"
            ),
            webId = "webId"
        )
        val entityMock = Mockito.mock(GistEntity::class.java)
        whenever(entityMapper.mapTo(any())).thenReturn(entityMock)
        localDataSet.unFavoriteGist(gistModel).test().run {
            assertComplete()
            assertNoErrors()

            verify(appDatabase, times(1)).favoriteGistDao()
            verify(favoriteGistDao, times(1)).delete(any())
            verify(entityMapper, times(1)).mapTo(eq(gistModel))
            verifyNoMoreInteractions(appDatabase, favoriteGistDao, entityMapper)
        }
    }

    @Test
    fun `should get favorite gists with success`() {
        val resultMockList =
            listOf(Mockito.mock(GistEntity::class.java), Mockito.mock(GistEntity::class.java))
        whenever(favoriteGistDao.getFavoriteGists()).thenReturn(
            resultMockList
        )
        whenever(modelMapper.mapTo(any())).thenReturn(Mockito.mock(GistModel::class.java))

        localDataSet.getFavoriteGists().test().run {
            assertComplete()
            assertNoErrors()

            assertValue {
                resultMockList.size == it.size
            }

            verify(appDatabase, times(1)).favoriteGistDao()
            verify(favoriteGistDao, times(1)).getFavoriteGists()
            verify(modelMapper, times(resultMockList.size)).mapTo(any())
            verifyNoMoreInteractions(appDatabase, favoriteGistDao, modelMapper)
        }
    }
}