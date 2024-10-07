package com.mert.trueform.di

import com.mert.trueform.data.remote.api.ApiService
import com.mert.trueform.data.repository.ExerciseRepositoryImpl
import com.mert.trueform.domain.repository.ExerciseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideExerciseRepository(apiService: ApiService): ExerciseRepository {
        return ExerciseRepositoryImpl(apiService)
    }
}