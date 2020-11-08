package br.com.adenilson.infrastructure.di

import dagger.Module

@Module(includes = [
    RepositoryModule::class,
    RemoteDataSetModule::class,
    LocalDataSetModule::class,
    MapperModule::class
])
abstract class InfrastructureModule