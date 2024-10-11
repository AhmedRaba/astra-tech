package com.training.astratech.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.training.astratech.data.api.ApiService
import com.training.astratech.data.data_source.remote.PostRemoteDataSource
import com.training.astratech.data.repos.PostRepositoryImpl
import com.training.astratech.domain.repos.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://task.astra-tech.net/fronendtask/public/api/"

    private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()


    @Provides
    @Singleton
    fun providesApiService(): ApiService {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesPostRemoteDataSource(apiService: ApiService): PostRemoteDataSource {
        return PostRemoteDataSource(apiService)
    }

    @Provides
    @Singleton
    fun providesPostRepository(postRemoteDataSource: PostRemoteDataSource): PostRepository {
        return PostRepositoryImpl(postRemoteDataSource)
    }


}