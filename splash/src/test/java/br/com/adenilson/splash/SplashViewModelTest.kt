package br.com.adenilson.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.adenilson.core.domain.ExecutorMock
import br.com.adenilson.splash.presentation.SplashViewModel
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SplashViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SplashViewModel

    private val loadingStateObserver: Observer<SplashViewModel.SplashState> = mock()
    private val executor = ExecutorMock()

    @Before
    fun setup() {
        viewModel = SplashViewModel(executor)
        viewModel.splashState.observeForever(loadingStateObserver)
    }

    @Test
    fun `should load call finish state after two seconds`() {
        viewModel.load()
        verify(loadingStateObserver, times(1)).onChanged(eq(SplashViewModel.SplashState.Finish))
    }
}