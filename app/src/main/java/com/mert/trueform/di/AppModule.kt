package com.mert.trueform.di

import com.mert.trueform.data.remote.api.ApiService
import com.mert.trueform.data.repository.ExerciseRepositoryImpl
import com.mert.trueform.domain.repository.ExerciseRepository
import com.mert.trueform.domain.usecase.GetExercisesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor { message ->
            Timber.tag("OkHttp").d(message)
        }
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://exercisedb.p.rapidapi.com")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideExerciseRepository(apiService: ApiService): ExerciseRepository {
        return ExerciseRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideGetExercisesUseCase(repository: ExerciseRepository): GetExercisesUseCase {
        return GetExercisesUseCase(repository)
    }
}