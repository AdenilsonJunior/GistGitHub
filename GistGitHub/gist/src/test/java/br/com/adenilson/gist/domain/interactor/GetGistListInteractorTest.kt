package br.com.adenilson.gist.domain.interactor

import br.com.adenilson.gist.data.GistRepository
import br.com.adenilson.gist.domain.mapper.GistRemoteMapper
import br.com.adenilson.gist.presentation.model.Gist
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

@RunWith(MockitoJUnitRunner::class)
class GetGistListInteractorTest {

    private lateinit var interactor: GetGistListInteractor

    private val repository: GistRepository = mock()
    private val mapper: GistRemoteMapper = mock()

    @Before
    fun setup() {
        interactor = GetGistListInteractorImpl(repository, mapper)
    }

    @Test
    fun `should get gist list without filter with success`() {
        val params = GetGistListInteractor.Params(
            usernameToFilter = "",
            page = 1,
            perPage = 30
        )
        val mockGistList =
            listOf(Mockito.mock(GistResponse::class.java), Mockito.mock(GistResponse::class.java))
        whenever(repository.getGistList(any(), any())).thenReturn(Single.just(mockGistList))
        whenever(mapper.mapTo(any())).thenReturn(Mockito.mock(Gist::class.java))
        interactor.execute(params).test().run {
            assertComplete()
            assertNoErrors()

            verify(repository, times(1)).getGistList(eq(params.page), eq(params.perPage))
            verify(mapper, times(mockGistList.size)).mapTo(any())
            verifyNoMoreInteractions(repository, mapper)
        }
    }

    @Test
    fun `should get gist list filtered with success`() {
        val params = GetGistListInteractor.Params(
            usernameToFilter = "filter",
            page = 1,
            perPage = 30
        )
        val mockGistList =
            listOf(Mockito.mock(GistResponse::class.java), Mockito.mock(GistResponse::class.java))
        whenever(repository.getGistsByUsername(any(), any(), any())).thenReturn(Single.just(mockGistList))
        whenever(mapper.mapTo(any())).thenReturn(Mockito.mock(Gist::class.java))
        interactor.execute(params).test().run {
            assertComplete()
            assertNoErrors()

            verify(repository, times(1)).getGistsByUsername(
                eq(params.usernameToFilter),
                eq(params.page),
                eq(params.perPage)
            )
            verify(mapper, times(mockGistList.size)).mapTo(any())
            verifyNoMoreInteractions(repository, mapper)
        }
    }

    @Test
    fun `should not get gist list given repository throws exception`() {
        val params = GetGistListInteractor.Params(
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
        interactor.execute(params).test().run {
            assertNotComplete()
            assertError(IOException::class.java)

            verify(repository, times(1)).getGistsByUsername(
                eq(params.usernameToFilter),
                eq(params.page),
                eq(params.perPage)
            )
            verifyNoMoreInteractions(repository)
            verifyZeroInteractions(mapper)
        }
    }
}