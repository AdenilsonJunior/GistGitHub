package br.com.adenilson.network.di

import br.com.adenilson.network.api.GistsAPI
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
abstract class NetworkModule {

    companion object {
        private const val API_URL = "https://api.github.com/"

        @Provides
        @Singleton
        fun providesHttpClient(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            return OkHttpClient.Builder().addInterceptor(interceptor).build()
        }

        @Singleton
        @Provides
        fun providesRetrofit(client: OkHttpClient): Retrofit =
            Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(client)
                .build()

        @Singleton
        @Provides
        fun providesGistAPI(retrofit: Retrofit): GistsAPI = retrofit.create(GistsAPI::class.java)
    }
}