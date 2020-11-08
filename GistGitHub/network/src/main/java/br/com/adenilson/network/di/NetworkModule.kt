package br.com.adenilson.network.di

import br.com.adenilson.network.api.GistsAPI
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
abstract class NetworkModule {

    companion object {
        private const val API_URL = "https://api.github.com/"

        @Singleton
        @Provides
        fun providesRetrofit(): Retrofit =
            Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()

        @Singleton
        @Provides
        fun providesGistAPI(retrofit: Retrofit): GistsAPI = retrofit.create(GistsAPI::class.java)
    }
}