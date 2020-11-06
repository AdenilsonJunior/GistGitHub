package br.com.adenilson.gist.presentation.details

import androidx.lifecycle.ViewModel
import br.com.adenilson.base.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class GistDetailsFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(GistDetailsViewModel::class)
    abstract fun bindGistDetailsViewModel(viewModel: GistDetailsViewModel): ViewModel
}