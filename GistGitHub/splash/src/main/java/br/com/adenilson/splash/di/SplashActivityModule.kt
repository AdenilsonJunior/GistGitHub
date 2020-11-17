package br.com.adenilson.splash.di

import androidx.lifecycle.ViewModel
import br.com.adenilson.base.presentation.viewmodel.ViewModelKey
import br.com.adenilson.splash.presentation.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SplashActivityModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel
}