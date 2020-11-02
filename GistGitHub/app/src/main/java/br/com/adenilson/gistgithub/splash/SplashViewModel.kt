package br.com.adenilson.gistgithub.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.adenilson.base.presentation.BaseViewModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.kotlin.subscribeBy
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashViewModel @Inject constructor() : BaseViewModel() {

    private val _splashState: MutableLiveData<SplashState> = MutableLiveData()
    val splashState: LiveData<SplashState> = _splashState

    fun load() {
        Completable.complete().delay(3, TimeUnit.SECONDS)
            .subscribeBy(
                onComplete = {
                    _splashState.postValue(SplashState.Finish)
                }
            )
    }

    sealed class SplashState {
        object Finish: SplashState()
    }

}