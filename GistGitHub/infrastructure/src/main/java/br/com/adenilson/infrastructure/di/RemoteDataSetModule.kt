package br.com.adenilson.infrastructure.di

import br.com.adenilson.infrastructure.dataset.GistRemoteDataSet
import br.com.adenilson.infrastructure.dataset.GistRemoteDataSetImpl
import br.com.adenilson.network.di.NetworkModule
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class])
abstract class RemoteDataSetModule {

    @Binds
    abstract fun bindGistRemoteDataSet(impl: GistRemoteDataSetImpl): GistRemoteDataSet
}