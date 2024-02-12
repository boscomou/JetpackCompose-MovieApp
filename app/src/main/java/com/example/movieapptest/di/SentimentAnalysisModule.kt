package com.example.movieapptest.di

import com.example.movieapptest.data.SentimantAnalysisApi.api.SentimentAnalysisApi
import com.example.movieapptest.data.SentimantAnalysisApi.api.SentimentAnalysisApiConstants
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


object SentimentAnalysisModule {

    @Provides
    @Singleton
    @Named("SentimentAnalysis")
    fun provideRetrofit(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(SentimentAnalysisApiConstants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())



    }

    @Provides
    @Singleton
    fun provideApi(@Named("SentimentAnalysis")builder: Retrofit.Builder): SentimentAnalysisApi {
        return builder
            .build()
            .create(SentimentAnalysisApi::class.java)
    }



}