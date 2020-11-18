package br.com.adenilson.gist.list.domain.datasource

import androidx.paging.PagingSource
import br.com.adenilson.base.domain.exception.UserHasNoGistsException
import br.com.adenilson.core.domain.ExecutorMock
import br.com.adenilson.gist.common.domain.model.Gist
import br.com.adenilson.gist.list.domain.usecase.GetGistListUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Observable
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class GistListDataSourceUnitTest {

    private lateinit var dataSource: GistListDataSource
    private val executor = ExecutorMock()
    private val getGistListUseCase: GetGistListUseCase = mock()

    @Before
    fun setup() {
        dataSource = GistListDataSource(executor, getGistListUseCase)
    }

    @Test
    fun `should dataSource get data from use case with success`() {
        whenever(getGistListUseCase.execute(any())).thenReturn(Observable.just(mockGistList))
        dataSource.loadSingle(PagingSource.LoadParams.Refresh(0, 30, false)).test().apply {
            assertComplete()
            assertNoErrors()

            argumentCaptor<GetGistListUseCase.Params> {
                verify(getGistListUseCase, times(1)).execute(capture())

                with(this.firstValue) {
                    assertEquals(0, page)
                    assertEquals("", usernameToFilter)
                    assertEquals(30, perPage)
                }

                assertValue {
                    it is PagingSource.LoadResult.Page &&
                            it.data.size == mockGistList.size
                }
            }
        }
    }

    @Test
    fun `should nextPage is not null given use case return gist list`() {
        val page = 0
        val nextPage = 1

        whenever(getGistListUseCase.execute(any())).thenReturn(Observable.just(mockGistList))
        dataSource.loadSingle(PagingSource.LoadParams.Refresh(page, 30, false)).test().apply {
            assertComplete()
            assertNoErrors()

            verify(getGistListUseCase, times(1)).execute(any())

            assertValue {
                it is PagingSource.LoadResult.Page &&
                        it.nextKey == nextPage
            }
        }
    }

    @Test
    fun `should call page 0 to use case given initial dataSource key is null`() {
        val nextPage = 1

        whenever(getGistListUseCase.execute(any())).thenReturn(Observable.just(mockGistList))
        dataSource.loadSingle(PagingSource.LoadParams.Refresh(null, 30, false)).test().apply {
            assertComplete()
            assertNoErrors()

            argumentCaptor<GetGistListUseCase.Params> {
                verify(getGistListUseCase, times(1)).execute(capture())

                with(this.firstValue) {
                    assertEquals(0, page)
                }

                assertValue {
                    it is PagingSource.LoadResult.Page &&
                            it.nextKey == nextPage
                }
            }
        }
    }

    @Test
    fun `should nextPage is null given use case returns a empty gist list`() {
        whenever(getGistListUseCase.execute(any())).thenReturn(Observable.just(listOf()))
        dataSource.loadSingle(PagingSource.LoadParams.Refresh(1, 30, false)).test().apply {
            assertComplete()
            assertNoErrors()

            verify(getGistListUseCase, times(1)).execute(any())

            assertValue {
                it is PagingSource.LoadResult.Page &&
                        it.nextKey == null
            }
        }
    }

    @Test
    fun `should pass page, userNameToFilter and perPage to use case with success`() {
        val usernameToFilter = "test"
        val perPage = 15
        val page = 7

        whenever(getGistListUseCase.execute(any())).thenReturn(Observable.just(mockGistList))
        dataSource.apply {
            this.usernameToFilter = usernameToFilter
            this.perPage = perPage
        }.loadSingle(PagingSource.LoadParams.Refresh(page, 30, false)).test().apply {
            assertComplete()
            assertNoErrors()

            argumentCaptor<GetGistListUseCase.Params> {
                verify(getGistListUseCase, times(1)).execute(capture())
                with(this) {
                    assertEquals(usernameToFilter, firstValue.usernameToFilter)
                    assertEquals(perPage, firstValue.perPage)
                    assertEquals(page, firstValue.page)
                }
            }

            assertValue {
                it is PagingSource.LoadResult.Page
            }
        }
    }

    @Test
    fun `should not get list given use case throws error`() {
        whenever(getGistListUseCase.execute(any())).thenReturn(Observable.error(IOException()))
        dataSource.loadSingle(PagingSource.LoadParams.Refresh(1, 30, false)).test().apply {
            assertComplete()
            assertNoErrors()

            verify(getGistListUseCase, times(1)).execute(any())

            assertValue {
                it is PagingSource.LoadResult.Error &&
                        it.throwable is IOException
            }
        }
    }

    @Test
    fun `should throw UserHasNoGistException given use case returns a empty list and page is 0`() {
        whenever(getGistListUseCase.execute(any())).thenReturn(Observable.just(listOf()))
        dataSource.loadSingle(PagingSource.LoadParams.Refresh(0, 30, false)).test().apply {
            assertComplete()
            assertNoErrors()

            verify(getGistListUseCase, times(1)).execute(any())

            assertValue {
                it is PagingSource.LoadResult.Error &&
                        it.throwable is UserHasNoGistsException
            }
        }
    }

    @Test
    fun `should load success given use case returns a empty list by page is more than 0`() {
        whenever(getGistListUseCase.execute(any())).thenReturn(Observable.just(listOf()))
        dataSource.loadSingle(PagingSource.LoadParams.Refresh(1, 30, false)).test().apply {
            assertComplete()
            assertNoErrors()

            verify(getGistListUseCase, times(1)).execute(any())

            assertValue {
                it is PagingSource.LoadResult.Page &&
                        it.data.isEmpty()
            }
        }
    }

    private val mockGistList = listOf(
        Mockito.mock(Gist::class.java),
        Mockito.mock(Gist::class.java),
        Mockito.mock(Gist::class.java)
    )
}