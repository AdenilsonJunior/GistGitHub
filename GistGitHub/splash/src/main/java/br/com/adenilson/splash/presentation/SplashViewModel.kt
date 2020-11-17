package br.com.adenilson.splash.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.adenilson.base.presentation.viewmodel.BaseViewModel
import br.com.adenilson.core.domain.Executor
import br.com.adenilson.core.domain.Interactor
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.kotlin.subscribeBy
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val executor: Executor
) : BaseViewModel() {

    private val _splashState: MutableLiveData<SplashState> = MutableLiveData()
    val splashState: LiveData<SplashState> = _splashState

    fun load() {
        executor.execute(object : Interactor<Unit, Completable> {
            override fun execute(params: Unit): Completable {
                return Completable.complete()
            }
        }, Unit)
            .delay(2, TimeUnit.SECONDS)
            .subscribeBy {
                _splashState.postValue(SplashState.Finish)
            }
    }

    sealed class SplashState {
        object Finish : SplashState()
    }
}