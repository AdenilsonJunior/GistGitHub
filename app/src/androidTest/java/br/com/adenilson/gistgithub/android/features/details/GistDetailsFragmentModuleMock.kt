package br.com.adenilson.gistgithub.android.features.details

import androidx.lifecycle.ViewModel
import br.com.adenilson.base.presentation.viewmodel.ViewModelKey
import br.com.adenilson.gist.details.presentation.GistDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class GistDetailsFragmentModuleMock {

    @Binds
    @IntoMap
    @ViewModelKey(GistDetailsViewModel::class)
    abstract fun bindGistDetailsViewModel(viewModel: GistDetailsViewModel): ViewModel
}