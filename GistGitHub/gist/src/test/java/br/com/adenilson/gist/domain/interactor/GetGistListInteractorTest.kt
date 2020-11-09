package br.com.adenilson.gist.domain.interactor

import br.com.adenilson.data.model.GistModel
import br.com.adenilson.data.repository.GistRepository
import br.com.adenilson.gist.domain.mapper.GistMapper
import br.com.adenilson.gist.presentation.model.Gist
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
    private val mapper: GistMapper = mock()

    @Before
    fun setup() {
        interactor = GetGistListInteractorImpl(repository, mapper)
    }

    @Test
    fun `should get gist list without filter with success`() {
        val params = GetGistListInteractor.Params(
            usernameToFilter = "",
            page = 1
        )
        val mockGistList =
            listOf(Mockito.mock(GistModel::class.java), Mockito.mock(GistModel::class.java))
        whenever(repository.getGistList(any())).thenReturn(Single.just(mockGistList))
        whenever(mapper.mapTo(any())).thenReturn(Mockito.mock(Gist::class.java))
        interactor.execute(params).test().run {
            assertComplete()
            assertNoErrors()

            verify(repository, times(1)).getGistList(eq(params.page))
            verify(mapper, times(mockGistList.size)).mapTo(any())
            verifyNoMoreInteractions(repository, mapper)
        }
    }

    @Test
    fun `should get gist list filtered with success`() {
        val params = GetGistListInteractor.Params(
            usernameToFilter = "filter",
            page = 1
        )
        val mockGistList =
            listOf(Mockito.mock(GistModel::class.java), Mockito.mock(GistModel::class.java))
        whenever(repository.getGistsByUsername(any(), any())).thenReturn(Single.just(mockGistList))
        whenever(mapper.mapTo(any())).thenReturn(Mockito.mock(Gist::class.java))
        interactor.execute(params).test().run {
            assertComplete()
            assertNoErrors()

            verify(repository, times(1)).getGistsByUsername(
                eq(params.usernameToFilter),
                eq(params.page)
            )
            verify(mapper, times(mockGistList.size)).mapTo(any())
            verifyNoMoreInteractions(repository, mapper)
        }
    }

    @Test
    fun `should not get gist list given repository throws exception`() {
        val params = GetGistListInteractor.Params(
            usernameToFilter = "filter",
            page = 1
        )
        whenever(
            repository.getGistsByUsername(
                any(),
                any()
            )
        ).thenReturn(Single.error(IOException()))
        interactor.execute(params).test().run {
            assertNotComplete()
            assertError(IOException::class.java)

            verify(repository, times(1)).getGistsByUsername(
                eq(params.usernameToFilter),
                eq(params.page)
            )
            verifyNoMoreInteractions(repository)
            verifyZeroInteractions(mapper)
        }
    }
}