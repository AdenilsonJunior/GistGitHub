package br.com.adenilson.gist.data.di

import br.com.adenilson.gist.data.GistRepository
import br.com.adenilson.gist.data.GistRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindGistRepository(impl: GistRepositoryImpl): GistRepository
}