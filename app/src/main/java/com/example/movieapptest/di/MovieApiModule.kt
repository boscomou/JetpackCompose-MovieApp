package com.example.statetest2.di

import com.example.movieapptest.data.api.ApiConstants
import com.example.movieapptest.data.api.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MovieApiModule {

    @Provides
    @Singleton
    @Named("MovieApi")
    fun provideApiBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
    }

    @Provides
    @Singleton
    fun provideApi(@Named("MovieApi") builder: Retrofit.Builder): MovieApi {
        return builder
            .build()
            .create(MovieApi::class.java)
    }
}