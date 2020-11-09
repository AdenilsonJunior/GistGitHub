package br.com.adenilson.infrastructure.dataset

import br.com.adenilson.data.model.GistModel
import br.com.adenilson.infrastructure.mapper.GistResponseMapper
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

@RunWith(MockitoJUnitRunner::class)
class GistRemoteDataSetTest {

    private lateinit var dataSet: GistRemoteDataSet

    private val mapper: GistResponseMapper = mock()
    private val api: GistsAPI = mock()

    @Before
    fun setup() {
        dataSet = GistRemoteDataSetImpl(api, mapper)
    }

    @Test
    fun `should get gist list with success`() {
        val page = 1
        whenever(api.getGists(any())).thenReturn(Single.just(gistsResponse))
        whenever(mapper.mapTo(any())).thenReturn(gistsModel)
        dataSet.getGistList(page).test().run {
            assertComplete()
            assertNoErrors()

            verify(api, times(1)).getGists(eq(page))
            verify(mapper, times(3)).mapTo(any())
            verifyNoMoreInteractions(api, mapper)
        }
    }

    @Test
    fun `should get gist list with error`() {
        val page = 1
        whenever(api.getGists(any())).thenReturn(Single.error(IOException()))
        dataSet.getGistList(page).test().run {
            assertNotComplete()
            assertError(IOException::class.java)

            verify(api, times(1)).getGists(eq(page))
            verifyNoMoreInteractions(api)
            verifyZeroInteractions(mapper)
        }
    }

    @Test
    fun `should get gist list by username with success`() {
        val page = 1
        val username = "username"
        whenever(api.getGistsByUsername(any(), any())).thenReturn(Single.just(gistsResponse))
        whenever(mapper.mapTo(any())).thenReturn(gistsModel)
        dataSet.getGistsByUsername(username, page).test().run {
            assertComplete()
            assertNoErrors()

            verify(api, times(1)).getGistsByUsername(eq(username), eq(page))
            verify(mapper, times(3)).mapTo(any())
            verifyNoMoreInteractions(api, mapper)
        }
    }

    @Test
    fun `should get gist list by username with error`() {
        val page = 1
        val username = "username"
        whenever(api.getGistsByUsername(any(), any())).thenReturn(Single.error(IOException()))
        dataSet.getGistsByUsername(username, page).test().run {
            assertNotComplete()
            assertError(IOException::class.java)

            verify(api, times(1)).getGistsByUsername(eq(username), eq(page))
            verifyNoMoreInteractions(api)
            verifyZeroInteractions(mapper)
        }
    }

    private val gistsResponse = listOf(
        Mockito.mock(GistResponse::class.java),
        Mockito.mock(GistResponse::class.java),
        Mockito.mock(GistResponse::class.java)
    )

    private val gistsModel = Mockito.mock(GistModel::class.java)
}