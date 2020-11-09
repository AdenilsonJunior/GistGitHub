package br.com.adenilson.gist.domain.interactor

import br.com.adenilson.data.model.GistModel
import br.com.adenilson.data.repository.GistRepository
import br.com.adenilson.gist.domain.mapper.GistModelMapper
import br.com.adenilson.gist.presentation.model.Gist
import br.com.adenilson.gist.presentation.model.Owner
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Completable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.Date

@RunWith(MockitoJUnitRunner::class)
class FavoriteGistInteractorTest {

    private lateinit var interactor: FavoriteGistInteractor
    private val mapper: GistModelMapper = mock()
    private val repository: GistRepository = mock()

    @Before
    fun setup() {
        interactor = FavoriteGistInteractorImpl(repository, mapper)
    }

    @Test
    fun `should favorite a gist`() {
        val gist = Gist(
            webId = "",
            description = "",
            lastUpdate = Date(),
            favorite = false,
            owner = Mockito.mock(Owner::class.java),
            files = listOf()
        )
        whenever(repository.favoriteGist(any())).thenReturn(Completable.complete())
        whenever(mapper.mapTo(any())).thenReturn(Mockito.mock(GistModel::class.java))
        interactor.execute(gist).test().run {
            assertComplete()
            assertNoErrors()

            verify(repository, times(1)).favoriteGist(any())
            verify(mapper, times(1)).mapTo(eq(gist))
            verifyNoMoreInteractions(repository, mapper)
        }
    }

    @Test
    fun `should unFavorite a gist`() {
        val gist = Gist(
            webId = "",
            description = "",
            lastUpdate = Date(),
            favorite = true,
            owner = Mockito.mock(Owner::class.java),
            files = listOf()
        )
        whenever(repository.unFavoriteGist(any())).thenReturn(Completable.complete())
        whenever(mapper.mapTo(any())).thenReturn(Mockito.mock(GistModel::class.java))
        interactor.execute(gist).test().run {
            assertComplete()
            assertNoErrors()

            verify(repository, times(1)).unFavoriteGist(any())
            verify(mapper, times(1)).mapTo(eq(gist))
            verifyNoMoreInteractions(repository, mapper)
        }
    }

    @Test
    fun `should not unFavorite given interactor throws exception`() {
        val gist = Gist(
            webId = "",
            description = "",
            lastUpdate = Date(),
            favorite = true,
            owner = Mockito.mock(Owner::class.java),
            files = listOf()
        )
        whenever(repository.unFavoriteGist(any())).thenReturn(Completable.error(Exception()))
        whenever(mapper.mapTo(any())).thenReturn(Mockito.mock(GistModel::class.java))
        interactor.execute(gist).test().run {
            assertNotComplete()
            assertError(Exception::class.java)

            verify(repository, times(1)).unFavoriteGist(any())
            verify(mapper, times(1)).mapTo(any())
            verifyNoMoreInteractions(repository, mapper)
        }
    }
}