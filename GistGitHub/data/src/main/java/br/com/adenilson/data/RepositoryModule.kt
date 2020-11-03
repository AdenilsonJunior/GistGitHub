package br.com.adenilson.data

import br.com.adenilson.data.repository.GistRepository
import br.com.adenilson.data.repository.GistRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindGistRepository(impl: GistRepositoryImpl): GistRepository
}