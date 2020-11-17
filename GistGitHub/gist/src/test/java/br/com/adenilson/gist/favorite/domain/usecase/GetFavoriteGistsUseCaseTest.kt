package br.com.adenilson.gist.favorite.domain.usecase

import br.com.adenilson.database.entity.GistEntity
import br.com.adenilson.gist.common.data.repository.GistRepository
import br.com.adenilson.gist.favorite.domain.mapper.GistLocalMapper
import br.com.adenilson.gist.common.domain.model.Gist
import com.nhaarman.mockitokotlin2.any
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

@RunWith(MockitoJUnitRunner::class)
class GetFavoriteGistsUseCaseTest {

    private lateinit var useCase: GetFavoriteGistsUseCase

    private val repository: GistRepository = mock()
    private val mapper: GistLocalMapper = mock()

    @Before
    fun setup() {
        useCase = GetFavoriteGistsUseCaseImpl(repository, mapper)
    }

    @Test
    fun `should get favorite gists with success`() {
        val gistsModel =
            listOf(Mockito.mock(GistEntity::class.java), Mockito.mock(GistEntity::class.java))
        whenever(repository.getFavoriteGists()).thenReturn(
            Single.just(gistsModel)
        )
        whenever(mapper.mapTo(any())).thenReturn(Mockito.mock(Gist::class.java))
        useCase.execute(Unit).test().run {
            assertComplete()
            assertNoErrors()

            verify(repository, times(1)).getFavoriteGists()
            verify(mapper, times(gistsModel.size)).mapTo(any())
            verifyNoMoreInteractions(repository, mapper)
        }
    }

    @Test
    fun `should not get favorite gist list given repository throws exception`() {
        whenever(repository.getFavoriteGists()).thenReturn(
            Single.error(IOException())
        )
        useCase.execute(Unit).test().run {
            assertNotComplete()
            assertError(IOException::class.java)

            verify(repository, times(1)).getFavoriteGists()
            verifyNoMoreInteractions(repository)
            verifyZeroInteractions(mapper)
        }
    }
}