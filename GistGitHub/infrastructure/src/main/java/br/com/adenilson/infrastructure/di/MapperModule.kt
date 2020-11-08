package br.com.adenilson.infrastructure.di

import br.com.adenilson.infrastructure.mapper.GistEntityMapper
import br.com.adenilson.infrastructure.mapper.GistEntityMapperImpl
import br.com.adenilson.infrastructure.mapper.GistModelListMapper
import br.com.adenilson.infrastructure.mapper.GistModelListMapperImpl
import br.com.adenilson.infrastructure.mapper.GistModelMapper
import br.com.adenilson.infrastructure.mapper.GistModelMapperImpl
import dagger.Binds
import dagger.Module

@Module
abstract class MapperModule {

    @Binds
    abstract fun bindGistEntityMapper(impl: GistEntityMapperImpl): GistEntityMapper

    @Binds
    abstract fun bindGistModelListMapper(impl: GistModelListMapperImpl): GistModelListMapper

    @Binds
    abstract fun bindGistModelMapper(impl: GistModelMapperImpl): GistModelMapper
}