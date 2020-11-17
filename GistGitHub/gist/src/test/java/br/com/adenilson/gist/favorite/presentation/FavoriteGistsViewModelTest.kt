package br.com.adenilson.gist.favorite.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.adenilson.core.domain.ExecutorMock
import br.com.adenilson.gist.common.domain.usecase.FavoriteGistUseCase
import br.com.adenilson.gist.favorite.domain.usecase.GetFavoriteGistsUseCase
import br.com.adenilson.gist.common.domain.model.Gist
import br.com.adenilson.gist.common.domain.model.Owner
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteGistsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: FavoriteGistsViewModel

    private val executor = ExecutorMock()
    private val getFavoriteGistsUseCase: GetFavoriteGistsUseCase = mock()
    private val favoriteGistsUseCase: FavoriteGistUseCase = mock()

    private val favoriteObserver: Observer<FavoriteGistsViewModel.FavoriteGistsState> = mock()
    private val loadingObserver: Observer<FavoriteGistsViewModel.LoadingState> = mock()

    @Before
    fun setup() {
        viewModel =
            FavoriteGistsViewModel(executor, getFavoriteGistsUseCase, favoriteGistsUseCase)
        viewModel.favoriteGistsState.observeForever(favoriteObserver)
        viewModel.loadingState.observeForever(loadingObserver)
    }

    @Test
    fun `should unFavorite a gist with success`() {
        val gist = Gist(
            webId = "",
            description = "",
            lastUpdate = null,
            owner = Mockito.mock(Owner::class.java),
            files = listOf(),
            favorite = false
        )
        whenever(favoriteGistsUseCase.execute(any())).thenReturn(Completable.complete())
        viewModel.favoriteGist(gist)
        verify(favoriteObserver, times(1)).onChanged(
            eq(FavoriteGistsViewModel.FavoriteGistsState.UnFavorite(gist))
        )
    }

    @Test
    fun `should favorite a gist with success`() {
        val gist = Gist(
            webId = "",
            description = "",
            lastUpdate = null,
            owner = Mockito.mock(Owner::class.java),
            files = listOf(),
            favorite = true
        )
        whenever(favoriteGistsUseCase.execute(any())).thenReturn(Completable.complete())
        viewModel.favoriteGist(gist)
        verify(favoriteObserver, times(1)).onChanged(
            eq(FavoriteGistsViewModel.FavoriteGistsState.Favorite(gist))
        )
    }

    @Test
    fun `should not favorite a gist given use case throws error`() {
        val gist = Gist(
            webId = "",
            description = "",
            lastUpdate = null,
            owner = Mockito.mock(Owner::class.java),
            files = listOf(),
            favorite = false
        )
        val exception = Exception()
        whenever(favoriteGistsUseCase.execute(any())).thenReturn(Completable.error(exception))
        viewModel.favoriteGist(gist)
        verify(favoriteObserver, times(1)).onChanged(
            eq(
                FavoriteGistsViewModel.FavoriteGistsState.Error(exception)
            )
        )
    }

    @Test
    fun `should load favorite gist list with success`() {
        whenever(getFavoriteGistsUseCase.execute(any())).thenReturn(
            Single.just(gists)
        )
        viewModel.loadFavorites()
        verify(loadingObserver, times(1)).onChanged(eq(FavoriteGistsViewModel.LoadingState.Start))
        verify(loadingObserver, times(1)).onChanged(eq(FavoriteGistsViewModel.LoadingState.End))
        verify(favoriteObserver, times(1)).onChanged(eq(FavoriteGistsViewModel.FavoriteGistsState.Loaded(gists)))
    }

    @Test
    fun `should return empty state given favorite list is empty`() {
        whenever(getFavoriteGistsUseCase.execute(any())).thenReturn(
            Single.just(listOf())
        )
        viewModel.loadFavorites()
        verify(loadingObserver, times(1)).onChanged(eq(FavoriteGistsViewModel.LoadingState.Start))
        verify(loadingObserver, times(1)).onChanged(eq(FavoriteGistsViewModel.LoadingState.End))
        verify(favoriteObserver, times(1)).onChanged(eq(FavoriteGistsViewModel.FavoriteGistsState.Empty))
    }

    @Test
    fun `should return error state given favorite list throws error`() {
        val exception = Exception()
        whenever(getFavoriteGistsUseCase.execute(any())).thenReturn(
            Single.error(exception)
        )
        viewModel.loadFavorites()
        verify(loadingObserver, times(1)).onChanged(eq(FavoriteGistsViewModel.LoadingState.Start))
        verify(loadingObserver, times(1)).onChanged(eq(FavoriteGistsViewModel.LoadingState.End))
        verify(favoriteObserver, times(1)).onChanged(eq(FavoriteGistsViewModel.FavoriteGistsState.Error(exception)))
    }

    private val gists = listOf(
        Mockito.mock(Gist::class.java),
        Mockito.mock(Gist::class.java),
        Mockito.mock(Gist::class.java),
        Mockito.mock(Gist::class.java)
    )
}