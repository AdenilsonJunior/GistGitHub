package br.com.adenilson.infrastructure.di

import br.com.adenilson.database.AppDatabaseModule
import br.com.adenilson.infrastructure.dataset.GistLocalDataSet
import br.com.adenilson.infrastructure.dataset.GistLocalDataSetImpl
import dagger.Binds
import dagger.Module

@Module(includes = [AppDatabaseModule::class])
abstract class LocalDataSetModule {

    @Binds
    abstract fun bindGistLocalDataSet(impl: GistLocalDataSetImpl): GistLocalDataSet
}