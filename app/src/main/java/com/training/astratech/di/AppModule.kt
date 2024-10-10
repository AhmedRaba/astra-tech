package com.training.astratech.di

import com.training.astratech.data.api.ApiService
import com.training.astratech.data.data_source.remote.PostRemoteDataSource
import com.training.astratech.data.repos.PostRepository
import com.training.astratech.data.repos.PostRepositoryImpl
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

    @Provides
    @Singleton
    fun providesApiService(): ApiService {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
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