package br.com.adenilson.infrastructure.di

import br.com.adenilson.data.repository.GistRepository
import br.com.adenilson.infrastructure.repository.GistDataRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindGistRepository(impl: GistDataRepository): GistRepository
}