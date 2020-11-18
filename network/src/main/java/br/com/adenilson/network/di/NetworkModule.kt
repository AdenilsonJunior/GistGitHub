package br.com.adenilson.network.di

import br.com.adenilson.core.di.APIUrl
import br.com.adenilson.network.api.GistsAPI
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
object NetworkModule {

    @APIUrl
    @Provides
    fun providesApiUrl(): String = "https://api.github.com/"

    @Singleton
    @Provides
    fun providesHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(client: OkHttpClient, @APIUrl apiUrl: String): Retrofit = Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(client)
            .build()

    @Singleton
    @Provides
    fun providesGistAPI(retrofit: Retrofit): GistsAPI = retrofit.create(GistsAPI::class.java)
}