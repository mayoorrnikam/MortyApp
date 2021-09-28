package com.example.mortyapp.di

import com.example.mortyapp.BuildConfig
import com.example.mortyapp.common.Constants
import com.example.mortyapp.network.MortyAPIService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MortyAppModule {

    @Singleton
    @Provides
    @Named("useThisString")
    fun provideString(
        @Named("useThisAnotherString")anotherString: String
    ) : String {
        return anotherString
    }

    @Singleton
    @Provides
    @Named("useThisAnotherString")
    fun provideAnotherString() = "This is another Hilt injected string"

    @Singleton
    @Provides
    fun provideMortyService(
        retrofit: Retrofit
    ) : MortyAPIService{
        return retrofit.create(MortyAPIService::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ) : Retrofit{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Singleton
    @Provides
    fun provideMoshi() : Moshi{
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ) : OkHttpClient{

        return if (BuildConfig.DEBUG) {
            OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()
        } else {
            OkHttpClient.Builder()
                .build()
        }
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor() : HttpLoggingInterceptor{
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }
}