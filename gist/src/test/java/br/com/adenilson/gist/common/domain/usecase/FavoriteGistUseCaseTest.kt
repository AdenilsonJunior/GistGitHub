package br.com.adenilson.gist.common.domain.usecase

import br.com.adenilson.database.entity.GistEntity
import br.com.adenilson.gist.common.data.repository.GistRepository
import br.com.adenilson.gist.common.domain.mapper.GistEntityMapper
import br.com.adenilson.gist.common.domain.model.Gist
import br.com.adenilson.gist.common.domain.model.Owner
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Completable
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteGistUseCaseTest {

    private lateinit var useCase: FavoriteGistUseCase
    private val mapper: GistEntityMapper = mock()
    private val repository: GistRepository = mock()

    @Before
    fun setup() {
        useCase = FavoriteGistUseCaseImpl(repository, mapper)
    }

    @Test
    fun `should favorite a gist and change model attribute`() {
        val gist = Gist(
            webId = "",
            description = "",
            htmlUrl = "htmlUrl",
            favorite = false,
            owner = Mockito.mock(Owner::class.java),
            files = listOf()
        )
        whenever(repository.favoriteGist(any())).thenReturn(Completable.complete())
        whenever(mapper.mapTo(any())).thenReturn(Mockito.mock(GistEntity::class.java))
        useCase.execute(gist).test().run {
            assertComplete()
            assertNoErrors()

            verify(repository, times(1)).favoriteGist(any())
            verify(mapper, times(1)).mapTo(eq(gist))
            verifyNoMoreInteractions(repository, mapper)

            assertEquals(true, gist.favorite)
        }
    }

    @Test
    fun `should unFavorite a gist and change model attribute`() {
        val gist = Gist(
            webId = "",
            description = "",
            htmlUrl = "htmlUrl",
            favorite = true,
            owner = Mockito.mock(Owner::class.java),
            files = listOf()
        )
        whenever(repository.unFavoriteGist(any())).thenReturn(Completable.complete())
        whenever(mapper.mapTo(any())).thenReturn(Mockito.mock(GistEntity::class.java))
        useCase.execute(gist).test().run {
            assertComplete()
            assertNoErrors()

            verify(repository, times(1)).unFavoriteGist(any())
            verify(mapper, times(1)).mapTo(eq(gist))
            verifyNoMoreInteractions(repository, mapper)

            assertEquals(false, gist.favorite)
        }
    }

    @Test
    fun `should not unFavorite given repository throws exception`() {
        val gist = Gist(
            webId = "",
            description = "",
            htmlUrl = "htmlUrl",
            favorite = true,
            owner = Mockito.mock(Owner::class.java),
            files = listOf()
        )
        whenever(repository.unFavoriteGist(any())).thenReturn(Completable.error(Exception()))
        whenever(mapper.mapTo(any())).thenReturn(Mockito.mock(GistEntity::class.java))
        useCase.execute(gist).test().run {
            assertNotComplete()
            assertError(Exception::class.java)

            verify(repository, times(1)).unFavoriteGist(any())
            verify(mapper, times(1)).mapTo(any())
            verifyNoMoreInteractions(repository, mapper)
        }
    }

    @Test
    fun `should not change gist favorite attribute given favorite not works`() {
        val gist = Gist(
            webId = "",
            description = "",
            htmlUrl = "htmlUrl",
            favorite = false,
            owner = Mockito.mock(Owner::class.java),
            files = listOf()
        )
        whenever(repository.favoriteGist(any())).thenReturn(Completable.error(Exception()))
        whenever(mapper.mapTo(any())).thenReturn(Mockito.mock(GistEntity::class.java))
        useCase.execute(gist).test().run {
            assertNotComplete()
            assertError(Exception::class.java)

            assertEquals(false, gist.favorite)
        }
    }
}