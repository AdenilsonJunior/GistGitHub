package br.com.adenilson.gistgithub.splash

import androidx.lifecycle.ViewModel
import br.com.adenilson.base.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SplashActivityModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    internal abstract fun bindScoreFormViewModel(viewModel: SplashViewModel): ViewModel

}