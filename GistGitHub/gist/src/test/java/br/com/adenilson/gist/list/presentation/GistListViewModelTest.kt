package br.com.adenilson.gist.list.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.adenilson.core.domain.ExecutorMock
import br.com.adenilson.gist.list.domain.datasource.GistListDataSource
import br.com.adenilson.gist.commons.domain.interactor.FavoriteGistInteractor
import br.com.adenilson.gist.commons.domain.model.Gist
import br.com.adenilson.gist.commons.domain.model.Owner
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Completable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Provider

@RunWith(MockitoJUnitRunner::class)
class GistListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: GistListViewModel

    private val executor = ExecutorMock()
    private val provider: Provider<GistListDataSource> = mock()
    private val dataSource: GistListDataSource = mock()
    private val favoriteGistInteractor: FavoriteGistInteractor = mock()

    private val favoriteGistStateObserver: Observer<GistListViewModel.FavoriteGistState> = mock()

    @Before
    fun setup() {
        viewModel = GistListViewModel(executor, provider, favoriteGistInteractor)
        viewModel.favoriteGistState.observeForever(favoriteGistStateObserver)
    }

    @Test
    fun `should invalidate data source on make search`() {
        val query = "query"
        viewModel.dataSource = dataSource
        viewModel.makeSearch(query)
        verify(dataSource, times(1)).invalidate()
    }

    @Test
    fun `should favorite a gist with success`() {
        val gist = Gist(
            webId = "",
            description = "",
            lastUpdate = null,
            owner = Mockito.mock(Owner::class.java),
            files = listOf(),
            favorite = false
        )
        whenever(favoriteGistInteractor.execute(any())).thenReturn(Completable.complete())
        viewModel.favoriteClick(gist)
        verify(favoriteGistStateObserver, times(1)).onChanged(
            eq(
                GistListViewModel.FavoriteGistState.Success(gist)
            )
        )
    }

    @Test
    fun `should not favorite a gist given interactor throws error`() {
        val gist = Gist(
            webId = "",
            description = "",
            lastUpdate = null,
            owner = Mockito.mock(Owner::class.java),
            files = listOf(),
            favorite = false
        )
        whenever(favoriteGistInteractor.execute(any())).thenReturn(Completable.error(Exception()))
        viewModel.favoriteClick(gist)
        verify(favoriteGistStateObserver, times(1)).onChanged(
            eq(
                GistListViewModel.FavoriteGistState.Error
            )
        )
    }
}