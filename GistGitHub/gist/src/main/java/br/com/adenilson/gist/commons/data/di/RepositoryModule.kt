package br.com.adenilson.gist.commons.data.di

import br.com.adenilson.gist.commons.data.repository.GistRepository
import br.com.adenilson.gist.commons.data.repository.GistRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindGistRepository(impl: GistRepositoryImpl): GistRepository
}