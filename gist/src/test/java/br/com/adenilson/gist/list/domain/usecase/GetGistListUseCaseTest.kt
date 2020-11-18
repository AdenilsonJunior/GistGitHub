package br.com.adenilson.gist.list.domain.usecase

import br.com.adenilson.database.entity.GistEntity
import br.com.adenilson.gist.common.data.repository.GistRepository
import br.com.adenilson.gist.common.domain.mapper.GistRemoteMapper
import br.com.adenilson.gist.common.domain.model.Gist
import br.com.adenilson.network.response.GistResponse
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

@RunWith(MockitoJUnitRunner::class)
class GetGistListUseCaseTest {

    private lateinit var useCase: GetGistListUseCase

    private val repository: GistRepository = mock()
    private val mapper: GistRemoteMapper = mock()

    @Before
    fun setup() {
        useCase = GetGistListUseCaseImpl(repository, mapper)
    }

    @Test
    fun `should get gist list without filter with success`() {
        val params = GetGistListUseCase.Params(
            usernameToFilter = "",
            page = 1,
            perPage = 30
        )

        whenever(repository.getGistList(any(), any())).thenReturn(Single.just(mockGistList))
        whenever(repository.getFavoriteGists()).thenReturn(Single.just(mockFavoriteGistList))
        whenever(mapper.mapTo(any())).thenReturn(Mockito.mock(Gist::class.java))
        useCase.execute(params).test().run {
            assertComplete()
            assertNoErrors()

            verify(repository, times(1)).getGistList(eq(params.page), eq(params.perPage))
            verify(repository, times(1)).getFavoriteGists()
            verify(mapper, times(mockGistList.size)).mapTo(any())
            verifyNoMoreInteractions(repository, mapper)
        }
    }

    @Test
    fun `should get gist list filtered with success`() {
        val params = GetGistListUseCase.Params(
            usernameToFilter = "filter",
            page = 1,
            perPage = 30
        )
        whenever(repository.getGistsByUsername(any(), any(), any())).thenReturn(
            Single.just(
                mockGistList
            )
        )
        whenever(repository.getFavoriteGists()).thenReturn(Single.just(mockFavoriteGistList))
        whenever(mapper.mapTo(any())).thenReturn(Mockito.mock(Gist::class.java))
        useCase.execute(params).test().run {
            assertComplete()
            assertNoErrors()

            verify(repository, times(1)).getGistsByUsername(
                eq(params.usernameToFilter),
                eq(params.page),
                eq(params.perPage)
            )
            verify(repository, times(1)).getFavoriteGists()
            verify(mapper, times(mockGistList.size)).mapTo(any())
            verifyNoMoreInteractions(repository, mapper)
        }
    }

    @Test
    fun `should not get gist list given repository gist list throws exception`() {
        val params = GetGistListUseCase.Params(
            usernameToFilter = "filter",
            page = 1,
            perPage = 30
        )
        whenever(
            repository.getGistsByUsername(
                any(),
                any(),
                any()
            )
        ).thenReturn(Single.error(IOException()))
        whenever(repository.getFavoriteGists()).thenReturn(Single.just(mockFavoriteGistList))
        useCase.execute(params).test().run {
            assertNotComplete()
            assertError(IOException::class.java)

            verify(repository, times(1)).getGistsByUsername(
                eq(params.usernameToFilter),
                eq(params.page),
                eq(params.perPage)
            )
            verify(repository, times(1)).getFavoriteGists()
            verifyNoMoreInteractions(repository)
            verifyZeroInteractions(mapper)
        }
    }

    @Test
    fun `should get gist list even repository favorite gist list throws exception`() {
        val params = GetGistListUseCase.Params(
            usernameToFilter = "filter",
            page = 1,
            perPage = 30
        )
        whenever(
            repository.getGistsByUsername(
                any(),
                any(),
                any()
            )
        ).thenReturn(Single.just(mockGistList))
        whenever(repository.getFavoriteGists()).thenReturn(Single.error(IOException()))
        useCase.execute(params).test().run {
            assertComplete()
            assertNoErrors()

            verify(repository, times(1)).getGistsByUsername(
                eq(params.usernameToFilter),
                eq(params.page),
                eq(params.perPage)
            )
            verify(repository, times(1)).getFavoriteGists()
            verifyNoMoreInteractions(repository)
            verifyZeroInteractions(mapper)
        }
    }

    private val mockGistList =
        listOf(Mockito.mock(GistResponse::class.java), Mockito.mock(GistResponse::class.java))
    private val mockFavoriteGistList = listOf(Mockito.mock(GistEntity::class.java))
}